package com.learning.repertoire_manager.works.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import com.learning.repertoire_manager.works.dto.CatalogWorkResponseDto;
import com.learning.repertoire_manager.works.service.CatalogWorkService;
import java.util.UUID;

@RestController
@RequestMapping("/api/catalog-works")
@RequiredArgsConstructor
public class CatalogWorksController {
    private final CatalogWorkService workService;

    @GetMapping
    public Page<CatalogWorkResponseDto> getWorks(
            @RequestParam(required = false) String composer,
            @RequestParam(required = false) String technique,
            @RequestParam(required = false) String instrument,
            @PageableDefault(size = 20) Pageable pageable) {

        return workService.getWorksWithFilters(composer, technique, instrument, pageable);
    }

    @GetMapping("/{id}")
    public CatalogWorkResponseDto getWorkById(@PathVariable UUID id) {
        return workService.getWorkById(id);
    }
}
