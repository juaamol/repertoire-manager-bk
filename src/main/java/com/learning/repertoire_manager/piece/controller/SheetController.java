package com.learning.repertoire_manager.piece.controller;

import com.learning.repertoire_manager.piece.service.SheetService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/pieces/{pieceId}/sheet")
@RequiredArgsConstructor
public class SheetController {

    private final SheetService sheetService;

    @PostMapping("/pdf")
    public void uploadPdf(
        @PathVariable UUID pieceId,
        @RequestParam("file") MultipartFile file
    ) {
        sheetService.uploadPdf(pieceId, file);
    }
}
