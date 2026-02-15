package com.learning.repertoire_manager.works.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class InstrumentationRequestDto {
    private UUID id;
    private String rank; // e.g., "I", "II", "Solo", "Optional", etc.
    private Integer quantity;
}