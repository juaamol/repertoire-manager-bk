package com.learning.repertoire_manager.works.service;

import com.learning.repertoire_manager.exception.ResourceNotFoundException;
import com.learning.repertoire_manager.works.dto.*;
import com.learning.repertoire_manager.works.model.*;
import com.learning.repertoire_manager.works.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CatalogComposerService {
    private final CatalogComposerRepository composerRepository;

    @Transactional(readOnly = true)
    public ComposerResponseDto getComposerById(UUID composerId) {
        CatalogComposer composer = composerRepository.findById(composerId)
                .orElseThrow(() -> new ResourceNotFoundException("Composer not found"));
        return toDto(composer);
    }

    @Transactional(readOnly = true)
    public Page<ComposerResponseDto> getComposers(
            String name,
            Pageable pageable) {

        return composerRepository.findByName(name, pageable).map(this::toDto);
    }

    private ComposerResponseDto toDto(CatalogComposer composer) {
        return ComposerResponseDto.builder()
                .id(composer.getId())
                .name(composer.getName())
                .epoch(composer.getEpoch())
                .birth(composer.getBirth())
                .death(composer.getDeath())
                .shortName(composer.getShortName())
                .build();
    }
}