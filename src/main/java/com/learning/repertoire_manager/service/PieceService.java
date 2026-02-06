package com.learning.repertoire_manager.service;

import com.learning.repertoire_manager.dto.*;
import com.learning.repertoire_manager.exception.ResourceNotFoundException;
import com.learning.repertoire_manager.model.*;
import com.learning.repertoire_manager.repository.PieceRepository;
import com.learning.repertoire_manager.repository.TechniqueRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PieceService {
        private final PieceRepository pieceRepository;
        private final TechniqueRepository techniqueRepository;

        @Transactional
        public PieceResponseDto createPiece(PieceCreateRequestDto request) {
                UUID userId = getCurrentUserId();

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

        public List<PieceResponseDto> getPiecesWithFilters(String composer, String technique) {
                UUID userId = getCurrentUserId();

                String safeComposer = (composer == null || composer.isBlank()) ? "" : composer;

                return pieceRepository
                                .findByUserIdAndOptionalFilters(userId, safeComposer, technique)
                                .stream()
                                .map(this::toDto)
                                .toList();
        }

        @Transactional
        public PieceResponseDto updatePiece(UUID pieceId, PieceUpdateRequestDto request) {
                UUID userId = getCurrentUserId();

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
                UUID userId = getCurrentUserId();
                pieceRepository.deleteByIdAndUser_Id(pieceId, userId);
        }

        @Transactional
        public PieceResponseDto addTechniqueToPiece(UUID pieceId, String techniqueName) {
                UUID userId = getCurrentUserId();
                Piece piece = pieceRepository
                                .findByIdAndUser_Id(pieceId, userId)
                                .orElseThrow(() -> new IllegalArgumentException("Piece not found"));

                Technique technique = techniqueRepository.findByName(techniqueName)
                                .orElseThrow(() -> new IllegalArgumentException("Technique not found"));

                piece.getTechniques().add(technique);
                return toDto(pieceRepository.save(piece));
        }

        @Transactional
        public PieceResponseDto removeTechniqueFromPiece(UUID pieceId, UUID techniqueId) {
                UUID userId = getCurrentUserId();
                Piece piece = pieceRepository
                                .findByIdAndUser_Id(pieceId, userId)
                                .orElseThrow(() -> new IllegalArgumentException("Piece not found"));

                Technique technique = techniqueRepository.findById(techniqueId)
                                .orElseThrow(() -> new IllegalArgumentException("Technique not found"));

                piece.getTechniques().remove(technique);
                return toDto(pieceRepository.save(piece));
        }

        private UUID getCurrentUserId() {
                return (UUID) SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();
        }

        private PieceResponseDto toDto(Piece piece) {
                return PieceResponseDto.builder()
                                .id(piece.getId())
                                .title(piece.getTitle())
                                .composer(piece.getComposer())
                                .difficulty(piece.getDifficulty().name())
                                .status(piece.getStatus().name())
                                .techniques(
                                                piece.getTechniques().stream()
                                                                .map(Technique::getName)
                                                                .toList())
                                .build();
        }
}
