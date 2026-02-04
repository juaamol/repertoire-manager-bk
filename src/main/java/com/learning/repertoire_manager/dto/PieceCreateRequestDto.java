package com.learning.repertoire_manager.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PieceCreateRequestDto {
    private UUID userId;
    private String title;
    private String composer;
    private String difficulty;
    private String status;
    private List<String> techniques;
}
