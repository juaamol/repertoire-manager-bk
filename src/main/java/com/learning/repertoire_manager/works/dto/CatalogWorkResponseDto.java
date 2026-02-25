package com.learning.repertoire_manager.works.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CatalogWorkResponseDto {
    UUID id;
    String title;
    String classification;
    ComposerWorkResponseDto composer;
    List<WorkSettingResponseDto> settings;
}
