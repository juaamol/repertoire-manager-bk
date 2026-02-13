package com.learning.repertoire_manager.works.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.learning.repertoire_manager.works.dto.WorkCreateRequestDto;
import com.learning.repertoire_manager.works.dto.WorkResponseDto;
import com.learning.repertoire_manager.works.dto.WorkUpdateRequestDto;
import com.learning.repertoire_manager.works.service.WorkService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/works")
@RequiredArgsConstructor
public class WorksController {
    private final WorkService workService;

    @GetMapping
    public List<WorkResponseDto> getWorks(
            @RequestParam(required = false) String composer,
            @RequestParam(required = false) String technique) {
        return workService.getWorksWithFilters(composer, technique);
    }

    @GetMapping("/{id}")
    public WorkResponseDto getWorkById(@PathVariable UUID id) {
        return workService.getWorkById(id);
    }

    @PostMapping
    public WorkResponseDto createWork(@RequestBody @Validated WorkCreateRequestDto request) {
        return workService.createWork(request);
    }

    @PutMapping("/{id}")
    public WorkResponseDto updateWork(
            @PathVariable UUID id,
            @RequestBody @Valid WorkUpdateRequestDto request) {
        return workService.updateWork(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWork(@PathVariable UUID id) {
        workService.deleteWork(id);
    }

    @PostMapping("/{id}/techniques")
    public WorkResponseDto addTechnique(
            @PathVariable UUID id,
            @RequestParam String techniqueName) {
        return workService.addTechniqueToWork(id, techniqueName);
    }

    @DeleteMapping("/{id}/techniques/{techniqueId}")
    public WorkResponseDto removeTechnique(
            @PathVariable UUID id,
            @PathVariable UUID techniqueId) {
        return workService.removeTechniqueFromWork(id, techniqueId);
    }

}
