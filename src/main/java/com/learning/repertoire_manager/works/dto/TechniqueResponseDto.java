package com.learning.repertoire_manager.works.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TechniqueResponseDto {
    UUID id;
    String name;
}
