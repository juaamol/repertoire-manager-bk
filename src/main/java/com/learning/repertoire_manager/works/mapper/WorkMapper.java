package com.learning.repertoire_manager.works.mapper;

import com.learning.repertoire_manager.works.dto.*;
import com.learning.repertoire_manager.works.model.*;
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
                .techniques(work.getTechniques().stream().map(Technique::getName).toList())
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