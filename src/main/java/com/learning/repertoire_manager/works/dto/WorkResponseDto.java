package com.learning.repertoire_manager.works.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkResponseDto {
    UUID id;
    String title;
    String subtitle;
    String composerName;
    String difficulty;
    String status;
    String notes;
    List<String> techniques;
    SheetResponseDto sheet;
}
