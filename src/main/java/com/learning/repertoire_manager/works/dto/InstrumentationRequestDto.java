package com.learning.repertoire_manager.works.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class InstrumentationRequestDto {
    private UUID id;
    private Integer quantity;
}