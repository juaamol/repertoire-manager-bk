package com.learning.repertoire_manager.works.dto;

import java.util.List;
import java.util.UUID;

public record PieceResponseDto(
        UUID id,
        String title,
        String composer,
        String difficulty,
        String status,
        List<String> techniques,
        SheetResponseDto sheet
) {}
