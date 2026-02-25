package com.learning.repertoire_manager.works.mapper;

import com.learning.repertoire_manager.works.dto.*;
import com.learning.repertoire_manager.works.model.*;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CatalogWorkMapper {
    public CatalogWorkResponseDto toDto(CatalogWork work) {
        CatalogComposer composer = work.getComposer();
        ComposerWorkResponseDto composerDto = ComposerWorkResponseDto.builder()
                .id(composer.getId())
                .name(composer.getName())
                .build();

        List<WorkSettingResponseDto> settings = work.getSettings()
                .stream()
                .map(this::settingToDto)
                .toList();

        return CatalogWorkResponseDto.builder()
                .id(work.getId())
                .title(work.getTitle())
                .classification(work.getClassification())
                .composer(composerDto)
                .settings(settings)
                .build();
    }

    private WorkSettingResponseDto settingToDto(WorkSetting setting) {
        List<SettingSlotResponseDto> slots = setting.getSlots().stream()
                .map(slot -> {
                    List<InstrumentationResponseDto> alternatives = slot.getAlternatives().stream().map(alternative -> {
                        Instrumentation instrumentation = alternative.getInstrumentation();
                        return InstrumentationResponseDto.builder()
                                .id(instrumentation.getId())
                                .name(instrumentation.getName())
                                .build();
                    }).toList();

                    return SettingSlotResponseDto.builder().alternatives(alternatives).build();
                }).toList();
        return WorkSettingResponseDto.builder().slots(slots).build();
    }
}