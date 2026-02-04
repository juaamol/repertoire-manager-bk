package com.learning.repertoire_manager.service;

import com.learning.repertoire_manager.dto.*;
import com.learning.repertoire_manager.model.*;
import com.learning.repertoire_manager.repository.PieceRepository;
import com.learning.repertoire_manager.repository.TechniqueRepository;
import com.learning.repertoire_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PieceService {
        private final PieceRepository pieceRepository;
        private final UserRepository userRepository;
        private final TechniqueRepository techniqueRepository;

        @Transactional
        public PieceResponseDto createPiece(PieceCreateRequestDto request) {

                User user = userRepository.findById(request.getUserId())
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                List<Technique> techniques = request.getTechniques().stream()
                                .map(name -> techniqueRepository.findByName(name)
                                                .orElseGet(() -> techniqueRepository.save(
                                                                Technique.builder().name(name).build())))
                                .toList();

                Piece piece = Piece.builder()
                                .user(user)
                                .title(request.getTitle())
                                .composer(request.getComposer())
                                .difficulty(Difficulty.fromString(request.getDifficulty()))
                                .status(Status.fromString(request.getStatus()))
                                .techniques(techniques)
                                .build();

                Piece saved = pieceRepository.save(piece);

                return PieceResponseDto.builder()
                                .id(saved.getId())
                                .title(saved.getTitle())
                                .composer(saved.getComposer())
                                .difficulty(saved.getDifficulty().name())
                                .status(saved.getStatus().name())
                                .techniques(
                                                saved.getTechniques().stream()
                                                                .map(Technique::getName)
                                                                .toList())
                                .build();
        }

        public List<PieceResponseDto> getPiecesForUser(UUID userId) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                return pieceRepository.findByUser(user).stream()
                                .map(piece -> PieceResponseDto.builder()
                                                .id(piece.getId())
                                                .title(piece.getTitle())
                                                .composer(piece.getComposer())
                                                .difficulty(piece.getDifficulty().name())
                                                .status(piece.getStatus().name())
                                                .techniques(
                                                                piece
                                                                                .getTechniques()
                                                                                .stream()
                                                                                .map(Technique::getName)
                                                                                .toList())
                                                .build())
                                .toList();
        }

        public List<PieceResponseDto> getPiecesWithFilters(UUID userId, String composer, String technique) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                String safeComposer = (composer == null || composer.isBlank()) ? "" : composer;
                List<Piece> pieces = pieceRepository.findByUserAndOptionalFilters(user, safeComposer, technique);

                return pieces.stream()
                                .map(piece -> PieceResponseDto.builder()
                                                .id(piece.getId())
                                                .title(piece.getTitle())
                                                .composer(piece.getComposer())
                                                .difficulty(piece.getDifficulty().name())
                                                .status(piece.getStatus().name())
                                                .techniques(piece.getTechniques().stream()
                                                                .map(t -> t.getName())
                                                                .toList())
                                                .build())
                                .toList();
        }

        public PieceResponseDto updatePiece(UUID pieceId, PieceUpdateRequestDto request) {
                Piece piece = pieceRepository.findById(pieceId)
                                .orElseThrow(() -> new IllegalArgumentException("Piece not found"));

                piece.setTitle(request.getTitle());
                piece.setComposer(request.getComposer());
                piece.setDifficulty(Difficulty.valueOf(request.getDifficulty()));
                piece.setStatus(Status.valueOf(request.getStatus()));

                piece = pieceRepository.save(piece);

                return PieceResponseDto.builder()
                                .id(piece.getId())
                                .title(piece.getTitle())
                                .composer(piece.getComposer())
                                .difficulty(piece.getDifficulty().name())
                                .status(piece.getStatus().name())
                                .techniques(piece.getTechniques().stream()
                                                .map(t -> t.getName())
                                                .toList())
                                .build();
        }

        public void deletePiece(UUID pieceId) {
                Piece piece = pieceRepository.findById(pieceId)
                                .orElseThrow(() -> new IllegalArgumentException("Piece not found"));

                pieceRepository.delete(piece);
        }

        public PieceResponseDto addTechniqueToPiece(UUID pieceId, String techniqueName) {
                Piece piece = pieceRepository.findById(pieceId)
                                .orElseThrow(() -> new IllegalArgumentException("Piece not found"));

                Technique technique = techniqueRepository.findByName(techniqueName)
                                .orElseThrow(() -> new IllegalArgumentException("Technique not found"));

                piece.getTechniques().add(technique);
                piece = pieceRepository.save(piece);

                return PieceResponseDto.builder()
                                .id(piece.getId())
                                .title(piece.getTitle())
                                .composer(piece.getComposer())
                                .difficulty(piece.getDifficulty().name())
                                .status(piece.getStatus().name())
                                .techniques(piece.getTechniques().stream().map(Technique::getName).toList())
                                .build();
        }

        public PieceResponseDto removeTechniqueFromPiece(UUID pieceId, UUID techniqueId) {
                Piece piece = pieceRepository.findById(pieceId)
                                .orElseThrow(() -> new IllegalArgumentException("Piece not found"));

                Technique technique = techniqueRepository.findById(techniqueId)
                                .orElseThrow(() -> new IllegalArgumentException("Technique not found"));

                piece.getTechniques().remove(technique);
                piece = pieceRepository.save(piece);

                return PieceResponseDto.builder()
                                .id(piece.getId())
                                .title(piece.getTitle())
                                .composer(piece.getComposer())
                                .difficulty(piece.getDifficulty().name())
                                .status(piece.getStatus().name())
                                .techniques(piece.getTechniques().stream().map(Technique::getName).toList())
                                .build();
        }

}
