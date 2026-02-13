package com.learning.repertoire_manager.works.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Data
public class WorkCreateRequestDto {
    @NotBlank(message = "title is required")
    private String title;
    private String subtitle;
    private String notes;
    private UUID composerId;
    private String composerName;

    @NotBlank(message = "difficulty is required")
    private String difficulty;

    @NotBlank(message = "status is required")
    private String status;

    @NotNull(message = "techniqueIds cannot be null")
    private List<UUID> techniqueIds;
}
