package com.learning.repertoire_manager.works.dto;

import lombok.Data;

@Data
public class PieceUpdateRequestDto {
    private String title;
    private String composer;
    private String difficulty;
    private String status;
}
