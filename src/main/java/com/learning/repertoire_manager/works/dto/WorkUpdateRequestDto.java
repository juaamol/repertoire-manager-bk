package com.learning.repertoire_manager.works.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkUpdateRequestDto {
    private String title;
    private String subtitle;
    private String notes;
    private UUID composerId;
    private String difficulty;
    private String status;
    private List<UUID> techniqueIds;
}
