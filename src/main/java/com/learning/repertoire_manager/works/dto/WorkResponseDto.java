package com.learning.repertoire_manager.works.dto;

import java.util.List;
import java.util.UUID;

public record WorkResponseDto(
        UUID id,
        String title,
        String composer,
        String difficulty,
        String status,
        List<String> techniques,
        SheetResponseDto sheet
) {}
