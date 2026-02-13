package com.learning.repertoire_manager.works.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.learning.repertoire_manager.works.dto.SheetPageResponseDto;
import com.learning.repertoire_manager.works.service.SheetService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/works/{workId}/sheet")
@RequiredArgsConstructor
public class SheetController {

    private final SheetService sheetService;

    @PostMapping("/pdf")
    public void uploadPdf(
            @PathVariable UUID workId,
            @RequestParam("file") MultipartFile file) {
        sheetService.uploadPdf(workId, file);
    }

    @PostMapping("/images")
    public void uploadImages(
            @PathVariable UUID workId,
            @RequestParam("files") List<MultipartFile> files) {
        sheetService.uploadImages(workId, files);
    }

    @GetMapping("/pdf")
    public ResponseEntity<Resource> downloadPdf(@PathVariable UUID workId) {
        Resource resource = sheetService.downloadPdf(workId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/images")
    public List<SheetPageResponseDto> listImagePages(@PathVariable UUID workId) {
        return sheetService.listImagePages(workId);
    }

    @GetMapping("/images/{pageId}")
    public ResponseEntity<Resource> downloadImagePage(@PathVariable UUID pageId) {
        Resource resource = sheetService.downloadImagePage(pageId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
