package com.learning.repertoire_manager.works.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import com.learning.repertoire_manager.works.dto.InstrumentationResponseDto;
import com.learning.repertoire_manager.works.dto.InstrumentationTreeDto;
import com.learning.repertoire_manager.works.service.InstrumentationService;

import java.util.UUID;

@RestController
@RequestMapping("/api/instrumentations")
@RequiredArgsConstructor
public class InstrumentationController {
    private final InstrumentationService instrumentationService;

    @GetMapping
    public InstrumentationTreeDto getTree(
            @RequestParam(required = true) String query) {

        return instrumentationService.getContextualTree(query);
    }

    @GetMapping("/{id}")
    public InstrumentationResponseDto findById(@PathVariable UUID id) {
        return instrumentationService.findById(id);
    }
}
