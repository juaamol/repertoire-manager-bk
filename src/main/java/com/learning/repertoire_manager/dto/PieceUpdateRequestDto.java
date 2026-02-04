package com.learning.repertoire_manager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PieceUpdateRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Composer is required")
    private String composer;

    @NotBlank(message = "Difficulty is required")
    private String difficulty;

    @NotBlank(message = "Status is required")
    private String status;
}
