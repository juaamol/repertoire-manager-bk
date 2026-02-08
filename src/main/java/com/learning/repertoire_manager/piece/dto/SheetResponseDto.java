package com.learning.repertoire_manager.piece.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SheetResponseDto {
    private String type;

    private String pdfPath;
    private String pdfFilename;
    private String pdfContentType;

    private List<SheetPageResponseDto> pages;

    @Data
    @Builder
    public static class SheetPageResponseDto {
        private int order;
        private String path;
        private String filename;
        private String contentType;
    }
}
