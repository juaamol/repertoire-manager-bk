package com.learning.repertoire_manager.works.service;

import com.learning.repertoire_manager.exception.ResourceNotFoundException;
import com.learning.repertoire_manager.security.UserContext;
import com.learning.repertoire_manager.works.dto.*;
import com.learning.repertoire_manager.works.mapper.WorkMapper;
import com.learning.repertoire_manager.works.model.*;
import com.learning.repertoire_manager.works.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserWorkTechniqueService {
    private final UserWorkRepository userWorkRepository;
    private final TechniqueRepository techniqueRepository;
    private final UserContext userContext;
    private final WorkMapper workMapper;

    @Transactional
    public WorkResponseDto addTechniqueToWork(UUID workId, UUID techniqueId) {
        UUID userId = userContext.getCurrentUserId();
        UserWork work = userWorkRepository.findByIdAndUserId(workId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Work not found"));

        Technique technique = techniqueRepository.findById(techniqueId)
                .orElseThrow(() -> new ResourceNotFoundException("Technique not found"));

        if (!work.getTechniques().contains(technique)) {
            work.getTechniques().add(technique);
        }

        return workMapper.toDto(userWorkRepository.save(work));
    }

    @Transactional
    public WorkResponseDto removeTechniqueFromWork(UUID workId, UUID techniqueId) {
        UUID userId = userContext.getCurrentUserId();
        UserWork work = userWorkRepository.findByIdAndUserId(workId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Work not found"));

        work.getTechniques().removeIf(t -> t.getId().equals(techniqueId));

        return workMapper.toDto(userWorkRepository.save(work));
    }
}