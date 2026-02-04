package com.learning.repertoire_manager.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Data
public class PieceCreateRequestDto {

    @NotNull(message = "userId is required")
    private UUID userId;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "composer is required")
    private String composer;

    @NotBlank(message = "difficulty is required")
    private String difficulty;

    @NotBlank(message = "status is required")
    private String status;

    @NotEmpty(message = "techniques list cannot be empty")
    private List<String> techniques;
}
