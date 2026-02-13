package com.learning.repertoire_manager.works.service;

import com.learning.repertoire_manager.exception.ResourceNotFoundException;
import com.learning.repertoire_manager.works.dto.PieceCreateRequestDto;
import com.learning.repertoire_manager.works.dto.PieceResponseDto;
import com.learning.repertoire_manager.works.dto.PieceUpdateRequestDto;
import com.learning.repertoire_manager.works.dto.SheetPageResponseDto;
import com.learning.repertoire_manager.works.dto.SheetResponseDto;
import com.learning.repertoire_manager.security.UserContext;
import com.learning.repertoire_manager.user.model.User;
import com.learning.repertoire_manager.works.model.*;
import com.learning.repertoire_manager.works.repository.PieceRepository;
import com.learning.repertoire_manager.works.repository.TechniqueRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PieceService {
        private final PieceRepository pieceRepository;
        private final UserContext userContext;
        private final TechniqueRepository techniqueRepository;

        @Transactional
        public PieceResponseDto createPiece(PieceCreateRequestDto request) {
                UUID userId = userContext.getCurrentUserId();

                List<Technique> techniques = request.getTechniques().stream()
                                .map(name -> techniqueRepository.findByName(name)
                                                .orElseGet(() -> techniqueRepository.save(
                                                                Technique.builder().name(name).build())))
                                .toList();

                Piece piece = Piece.builder()
                                .user(User.builder().id(userId).build())
                                .title(request.getTitle())
                                .composer(request.getComposer())
                                .difficulty(Difficulty.fromString(request.getDifficulty()))
                                .status(Status.fromString(request.getStatus()))
                                .techniques(techniques)
                                .build();

                return toDto(pieceRepository.save(piece));
        }

        @Transactional
        public PieceResponseDto getPieceById(UUID pieceId) {
                UUID userId = userContext.getCurrentUserId();
                Piece piece = pieceRepository
                                .findByIdAndUser_Id(pieceId, userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Piece not found"));

                return toDto(piece);

        }

        @Transactional
        public List<PieceResponseDto> getPiecesWithFilters(String composer, String technique) {
                UUID userId = userContext.getCurrentUserId();
                String safeComposer = (composer == null || composer.isBlank()) ? "" : composer;

                return pieceRepository
                                .findByUserIdAndOptionalFilters(userId, safeComposer, technique)
                                .stream()
                                .map(this::toDto)
                                .toList();
        }

        @Transactional
        public PieceResponseDto updatePiece(UUID pieceId, PieceUpdateRequestDto request) {
                UUID userId = userContext.getCurrentUserId();

                Piece piece = pieceRepository
                                .findByIdAndUser_Id(pieceId, userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Piece not found"));

                if (request.getTitle() != null)
                        piece.setTitle(request.getTitle());
                if (request.getComposer() != null)
                        piece.setComposer(request.getComposer());
                if (request.getDifficulty() != null)
                        piece.setDifficulty(Difficulty.fromString(request.getDifficulty()));
                if (request.getStatus() != null)
                        piece.setStatus(Status.fromString(request.getStatus()));

                return toDto(pieceRepository.save(piece));
        }

        @Transactional
        public void deletePiece(UUID pieceId) {
                UUID userId = userContext.getCurrentUserId();
                pieceRepository.deleteByIdAndUser_Id(pieceId, userId);
        }

        @Transactional
        public PieceResponseDto addTechniqueToPiece(UUID pieceId, String techniqueName) {
                UUID userId = userContext.getCurrentUserId();
                Piece piece = pieceRepository
                                .findByIdAndUser_Id(pieceId, userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Piece not found"));

                Technique technique = techniqueRepository.findByName(techniqueName)
                                .orElseThrow(() -> new ResourceNotFoundException("Technique not found"));

                piece.getTechniques().add(technique);
                return toDto(pieceRepository.save(piece));
        }

        @Transactional
        public PieceResponseDto removeTechniqueFromPiece(UUID pieceId, UUID techniqueId) {
                UUID userId = userContext.getCurrentUserId();
                Piece piece = pieceRepository
                                .findByIdAndUser_Id(pieceId, userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Piece not found"));

                Technique technique = techniqueRepository.findById(techniqueId)
                                .orElseThrow(() -> new ResourceNotFoundException("Technique not found"));

                piece.getTechniques().remove(technique);
                return toDto(pieceRepository.save(piece));
        }

        private PieceResponseDto toDto(Piece piece) {
                SheetResponseDto sheetDto = null;

                if (piece.getSheet() != null) {
                        Sheet sheet = piece.getSheet();

                        sheetDto = switch (sheet.getType()) {
                                case PDF -> new SheetResponseDto(
                                                sheet.getId(),
                                                SheetType.PDF,
                                                sheet.getPdfFilename(),
                                                null);

                                case IMAGES -> new SheetResponseDto(
                                                sheet.getId(),
                                                SheetType.IMAGES,
                                                null,
                                                sheet.getPages().stream()
                                                                .map(p -> new SheetPageResponseDto(
                                                                                p.getId(),
                                                                                p.getPageOrder()))
                                                                .toList());
                        };
                }

                return new PieceResponseDto(
                                piece.getId(),
                                piece.getTitle(),
                                piece.getComposer(),
                                piece.getDifficulty().name(),
                                piece.getStatus().name(),
                                piece.getTechniques().stream().map(Technique::getName).toList(),
                                sheetDto);
        }

}
