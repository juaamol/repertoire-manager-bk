package com.learning.repertoire_manager.piece.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PieceResponseDto {
    private UUID id;
    private String title;
    private String composer;
    private String difficulty;
    private String status;
    private List<String> techniques;
    private SheetResponseDto sheet;
}
