package com.learning.repertoire_manager.works.dto;

import java.util.UUID;

public record SheetPageResponseDto(
        UUID id,
        int pageOrder
) {}
