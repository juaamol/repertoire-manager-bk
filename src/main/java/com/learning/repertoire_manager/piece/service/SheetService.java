package com.learning.repertoire_manager.piece.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.learning.repertoire_manager.exception.AccessDeniedException;
import com.learning.repertoire_manager.piece.model.Piece;
import com.learning.repertoire_manager.piece.model.Sheet;
import com.learning.repertoire_manager.piece.model.SheetType;
import com.learning.repertoire_manager.piece.repository.PieceRepository;
import com.learning.repertoire_manager.piece.repository.SheetRepository;
import com.learning.repertoire_manager.security.UserContext;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SheetService {

    private final PieceRepository pieceRepository;
    private final SheetRepository sheetRepository;
    private final StorageService storageService;
    private final UserContext userContext;

    public void uploadPdf(UUID pieceId, MultipartFile file) {

        UUID userId = userContext.getCurrentUserId();

        Piece piece = pieceRepository
            .findByIdAndUser_Id(pieceId, userId)
            .orElseThrow(() -> new AccessDeniedException("Not your piece"));

        if (!"application/pdf".equals(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        if (sheetRepository.existsByPiece_Id(pieceId)) {
            throw new IllegalStateException("Sheet already exists for this piece");
        }

        String path = storageService.store(pieceId, file);

        Sheet sheet = Sheet.builder()
            .piece(piece)
            .type(SheetType.PDF)
            .pdfPath(path)
            .pdfFilename(file.getOriginalFilename())
            .pdfContentType(file.getContentType())
            .build();

        sheetRepository.save(sheet);
    }
}
