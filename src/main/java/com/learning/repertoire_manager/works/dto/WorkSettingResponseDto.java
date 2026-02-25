package com.learning.repertoire_manager.works.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkSettingResponseDto {
    private List<SettingSlotResponseDto> slots;
}

