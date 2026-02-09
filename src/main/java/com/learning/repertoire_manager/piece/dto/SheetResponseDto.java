package com.learning.repertoire_manager.piece.dto;

import java.util.List;
import java.util.UUID;

import com.learning.repertoire_manager.piece.model.SheetType;

public record SheetResponseDto(
        UUID id,
        SheetType type,
        String filename,
        List<SheetPageResponseDto> pages
) {}
