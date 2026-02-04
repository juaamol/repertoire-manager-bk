package com.learning.repertoire_manager.controller;

import com.learning.repertoire_manager.dto.PieceResponseDto;
import com.learning.repertoire_manager.service.PieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pieces")
@RequiredArgsConstructor
public class PiecesController {
    private final PieceService pieceService;

    @GetMapping
    public List<PieceResponseDto> getPieces(@RequestParam UUID userId) {
        return pieceService.getPiecesForUser(userId);
    }
}
