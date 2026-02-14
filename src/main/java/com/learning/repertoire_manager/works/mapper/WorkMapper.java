package com.learning.repertoire_manager.works.mapper;

import com.learning.repertoire_manager.works.dto.*;
import com.learning.repertoire_manager.works.model.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class WorkMapper {
    public WorkResponseDto toDto(UserWork work) {
        String composerName = work.getCatalogComposer() != null
                ? work.getCatalogComposer().getName()
                : work.getUserComposer().getName();

        return WorkResponseDto.builder()
                .id(work.getId())
                .title(work.getTitle())
                .subtitle(work.getSubtitle())
                .composerName(composerName)
                .difficulty(work.getDifficulty().toString())
                .status(work.getStatus().toString())
                .notes(work.getNotes())
                .instrumentation(work.getInstrumentations().stream()
                        .map(i -> {
                            Instrumentation instrumentation = i.getInstrumentation();
                            String name = instrumentation.getName();
                            UUID id = instrumentation.getId();
                            
                            return InstrumentationResponseDto.builder().name(name).id(id).build();
                        })
                        .toList())
                .techniques(work.getTechniques().stream()
                        .map(technique -> {
                            String name = technique.getName();
                            UUID id = technique.getId();
                            
                            return TechniqueResponseDto.builder().name(name).id(id).build();
                        })
                        .toList())
                .build();
    }

    public SheetResponseDto mapSheetToDto(Sheet sheet) {
        if (sheet == null)
            return null;

        return switch (sheet.getType()) {
            case PDF -> new SheetResponseDto(sheet.getId(), SheetType.PDF, sheet.getPdfFilename(), null);
            case IMAGES -> new SheetResponseDto(
                    sheet.getId(),
                    SheetType.IMAGES,
                    null,
                    sheet.getPages().stream()
                            .map(p -> new SheetPageResponseDto(p.getId(), p.getPageOrder()))
                            .toList());
        };
    }
}