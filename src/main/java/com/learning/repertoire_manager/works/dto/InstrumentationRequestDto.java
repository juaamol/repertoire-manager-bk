package com.learning.repertoire_manager.works.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InstrumentationRequestDto {
    @NotNull(message = "techniqueIds cannot be null")
    private UUID id;
    private Integer quantity;
}