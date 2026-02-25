package com.learning.repertoire_manager.works.service;

import com.learning.repertoire_manager.exception.ResourceNotFoundException;
import com.learning.repertoire_manager.works.dto.*;
import com.learning.repertoire_manager.works.model.*;
import com.learning.repertoire_manager.works.repository.*;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstrumentationService {
    private final InstrumentationRepository instrumentationRepository;

    public InstrumentationTreeDto getContextualTree(String query) {
        List<Instrumentation> flatList = instrumentationRepository.findFullBranchByName(query);

        Map<UUID, InstrumentationTreeDto> dtoMap = new HashMap<>();
        InstrumentationTreeDto absoluteRoot = null;

        if (flatList.isEmpty())
            throw new ResourceNotFoundException("Instrumentation not found");

        for (Instrumentation entity : flatList) {
            boolean isMatch = entity.getName().toLowerCase().contains(query.toLowerCase());
            InstrumentationTreeDto dto = InstrumentationTreeDto
                    .builder().id(entity.getId())
                    .name(entity.getName())
                    .level(entity.getLevel())
                    .matched(isMatch)
                    .children(new ArrayList<>())
                    .build();

            dtoMap.put(entity.getId(), dto);
        }

        for (Instrumentation entity : flatList) {
            InstrumentationTreeDto current = dtoMap.get(entity.getId());

            if (entity.getParent() != null && dtoMap.containsKey(entity.getParent().getId())) {
                InstrumentationTreeDto parent = dtoMap.get(entity.getParent().getId());
                parent.getChildren().add(current);
            }

            if (absoluteRoot == null || entity.getLevel() < absoluteRoot.getLevel()) {
                absoluteRoot = current;
            }
        }

        if (absoluteRoot == null)
            throw new ResourceNotFoundException("Instrumentation not found");

        return absoluteRoot;
    }

    public InstrumentationResponseDto findById(UUID id) {
        Instrumentation instrumentation = instrumentationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instrumentation not found"));
        return InstrumentationResponseDto.builder().id(instrumentation.getId()).name(instrumentation.getName()).build();
    }
}