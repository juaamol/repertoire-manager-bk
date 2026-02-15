package com.learning.repertoire_manager.works.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.learning.repertoire_manager.works.dto.WorkCreateRequestDto;
import com.learning.repertoire_manager.works.dto.WorkResponseDto;
import com.learning.repertoire_manager.works.dto.WorkUpdateRequestDto;
import com.learning.repertoire_manager.works.service.UserWorkService;

import java.util.UUID;

@RestController
@RequestMapping("/api/works")
@RequiredArgsConstructor
public class WorksController {
    private final UserWorkService workService;

    @GetMapping
    public Page<WorkResponseDto> getWorks(
            @RequestParam(required = false) String composer,
            @RequestParam(required = false) String technique,
            @RequestParam(required = false) String instrument,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 20) Pageable pageable) {

        return workService.getWorksWithFilters(
                composer, technique, instrument, difficulty, status, pageable);
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
}
