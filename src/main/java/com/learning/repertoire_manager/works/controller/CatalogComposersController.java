package com.learning.repertoire_manager.works.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import com.learning.repertoire_manager.works.dto.ComposerResponseDto;
import com.learning.repertoire_manager.works.service.CatalogComposerService;
import java.util.UUID;

@RestController
@RequestMapping("/api/catalog-composers")
@RequiredArgsConstructor
public class CatalogComposersController {
    private final CatalogComposerService composerService;

    @GetMapping
    public Page<ComposerResponseDto> getComposers(
            @RequestParam(required = false) String name,
            @PageableDefault(size = 20) Pageable pageable) {

        return composerService.getComposers(name, pageable);
    }

    @GetMapping("/{id}")
    public ComposerResponseDto getComposerById(@PathVariable UUID id) {
        return composerService.getComposerById(id);
    }
}
