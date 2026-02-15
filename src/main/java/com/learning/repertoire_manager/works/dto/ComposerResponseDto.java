package com.learning.repertoire_manager.works.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComposerResponseDto {
    private UUID id;
    private String name;
    private String shortName;
    private String epoch;
    private LocalDate birth;
    private LocalDate death;
}
