package com.learning.repertoire_manager.works.mapper;

import com.learning.repertoire_manager.works.dto.*;
import com.learning.repertoire_manager.works.model.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class CatalogWorkMapper {
    public CatalogWorkResponseDto toDto(CatalogWork work) {
        return CatalogWorkResponseDto.builder()
                .id(work.getId())
                .title(work.getTitle())
                .subtitle(work.getSubtitle())
                .composerName(work.getComposer().getName())
                .instrumentation(work.getInstrumentations().stream()
                        .map(i -> {
                            Instrumentation instrumentation = i.getInstrumentation();
                            String name = instrumentation.getName();
                            UUID id = instrumentation.getId();
                            
                            return InstrumentationResponseDto.builder().name(name).id(id).build();
                        })
                        .toList())
                .build();
    }
}