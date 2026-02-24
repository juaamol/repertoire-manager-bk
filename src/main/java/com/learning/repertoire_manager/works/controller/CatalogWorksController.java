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
    public Page<CatalogWorkResponseDto> searchWorks(
        @RequestParam(required = true) UUID composerId,
        @RequestParam(required = true) String query,
        @RequestParam(required = true) UUID[] instrumentationIds,
            @PageableDefault(size = 20) Pageable pageable) {

        return workService.search(query, composerId, instrumentationIds, pageable);
    }   

    @GetMapping("/{id}")
    public CatalogWorkResponseDto getWorkById(@PathVariable UUID id) {
        return workService.getWorkById(id);
    }
}
