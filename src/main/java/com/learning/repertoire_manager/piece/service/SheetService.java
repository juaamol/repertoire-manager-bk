package com.learning.repertoire_manager.piece.service;

import com.learning.repertoire_manager.exception.AccessDeniedException;
import com.learning.repertoire_manager.piece.model.Sheet;
import com.learning.repertoire_manager.piece.model.SheetPage;
import com.learning.repertoire_manager.piece.model.SheetType;
import com.learning.repertoire_manager.piece.model.Piece;
import com.learning.repertoire_manager.piece.repository.PieceRepository;
import com.learning.repertoire_manager.piece.repository.SheetRepository;
import com.learning.repertoire_manager.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        sheetRepository.deleteByPiece_Id(pieceId);

        String path = storageService.store(pieceId, file);

        Sheet sheet = Sheet.builder()
                .piece(piece)
                .type(SheetType.PDF)
                .pdfPath(path)
                .pdfFilename(file.getOriginalFilename())
                .pdfContentType(file.getContentType())
                .pages(new ArrayList<>())
                .build();

        sheetRepository.save(sheet);
    }

    public void uploadImages(UUID pieceId, List<MultipartFile> files) {

        UUID userId = userContext.getCurrentUserId();

        Piece piece = pieceRepository
                .findByIdAndUser_Id(pieceId, userId)
                .orElseThrow(() -> new AccessDeniedException("Not your piece"));

        if (files.isEmpty()) {
            throw new IllegalArgumentException("No images uploaded");
        }

        for (MultipartFile file : files) {
            if (!file.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("All files must be images");
            }
        }

        sheetRepository.deleteByPiece_Id(pieceId);

        Sheet sheet = Sheet.builder()
                .piece(piece)
                .type(SheetType.IMAGES)
                .pages(new ArrayList<>())
                .build();

        int order = 1;
        for (MultipartFile file : files) {
            String path = storageService.store(pieceId, file);

            SheetPage page = SheetPage.builder()
                    .sheet(sheet)
                    .pageOrder(order++)
                    .imagePath(path)
                    .originalFilename(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .build();

            sheet.getPages().add(page);
        }

        sheetRepository.save(sheet);
    }
}
