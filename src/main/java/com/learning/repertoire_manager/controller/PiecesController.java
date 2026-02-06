package com.learning.repertoire_manager.controller;

import com.learning.repertoire_manager.dto.*;
import com.learning.repertoire_manager.service.PieceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pieces")
@RequiredArgsConstructor
public class PiecesController {
    private final PieceService pieceService;

    @GetMapping
    public List<PieceResponseDto> getPieces(
            @RequestParam(required = false) String composer,
            @RequestParam(required = false) String technique) {
        return pieceService.getPiecesWithFilters(composer, technique);
    }

    @PostMapping
    public PieceResponseDto createPiece(@RequestBody @Validated PieceCreateRequestDto request) {
        return pieceService.createPiece(request);
    }

    @PutMapping("/{id}")
    public PieceResponseDto updatePiece(
            @PathVariable UUID id,
            @RequestBody @Valid PieceUpdateRequestDto request) {
        return pieceService.updatePiece(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePiece(@PathVariable UUID id) {
        pieceService.deletePiece(id);
    }

    @PostMapping("/{id}/techniques")
    public PieceResponseDto addTechnique(
            @PathVariable UUID id,
            @RequestParam String techniqueName) {
        return pieceService.addTechniqueToPiece(id, techniqueName);
    }

    @DeleteMapping("/{id}/techniques/{techniqueId}")
    public PieceResponseDto removeTechnique(
            @PathVariable UUID id,
            @PathVariable UUID techniqueId) {
        return pieceService.removeTechniqueFromPiece(id, techniqueId);
    }

}
