package com.learning.repertoire_manager.works.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InstrumentationRequestDto {
    @NotNull(message = "Instrumentation id is required")
    private UUID id;
    private String rank; // e.g., "I", "II", "Solo", "Optional", etc.
    private Integer quantity;
}