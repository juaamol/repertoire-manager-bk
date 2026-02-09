package com.learning.repertoire_manager.piece.dto;

import java.util.UUID;

public record SheetPageResponseDto(
        UUID id,
        int pageOrder
) {}
