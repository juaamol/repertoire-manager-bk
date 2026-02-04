package com.learning.repertoire_manager.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class PieceResponseDto {
    UUID id;
    String title;
    String composer;
    String difficulty;
    String status;
    List<String> techniques;
}
