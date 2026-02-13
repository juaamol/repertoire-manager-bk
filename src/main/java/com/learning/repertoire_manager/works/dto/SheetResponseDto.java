package com.learning.repertoire_manager.works.dto;

import java.util.List;
import java.util.UUID;

import com.learning.repertoire_manager.works.model.SheetType;

public record SheetResponseDto(
        UUID id,
        SheetType type,
        String filename,
        List<SheetPageResponseDto> pages
) {}
