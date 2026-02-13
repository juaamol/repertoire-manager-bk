package com.learning.repertoire_manager.works.service;

import com.learning.repertoire_manager.exception.ResourceNotFoundException;
import com.learning.repertoire_manager.works.dto.WorkCreateRequestDto;
import com.learning.repertoire_manager.works.dto.WorkResponseDto;
import com.learning.repertoire_manager.works.dto.WorkUpdateRequestDto;
import com.learning.repertoire_manager.works.dto.SheetPageResponseDto;
import com.learning.repertoire_manager.works.dto.SheetResponseDto;
import com.learning.repertoire_manager.security.UserContext;
import com.learning.repertoire_manager.user.model.User;
import com.learning.repertoire_manager.works.model.*;
import com.learning.repertoire_manager.works.repository.WorkRepository;
import com.learning.repertoire_manager.works.repository.TechniqueRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkService {
        private final WorkRepository workRepository;
        private final UserContext userContext;
        private final TechniqueRepository techniqueRepository;

        @Transactional
        public WorkResponseDto createWork(WorkCreateRequestDto request) {
                UUID userId = userContext.getCurrentUserId();

                List<Technique> techniques = request.getTechniques().stream()
                                .map(name -> techniqueRepository.findByName(name)
                                                .orElseGet(() -> techniqueRepository.save(
                                                                Technique.builder().name(name).build())))
                                .toList();

                Work work = Work.builder()
                                .user(User.builder().id(userId).build())
                                .title(request.getTitle())
                                .composer(request.getComposer())
                                .difficulty(Difficulty.fromString(request.getDifficulty()))
                                .status(Status.fromString(request.getStatus()))
                                .techniques(techniques)
                                .build();

                return toDto(workRepository.save(work));
        }

        @Transactional
        public WorkResponseDto getWorkById(UUID workId) {
                UUID userId = userContext.getCurrentUserId();
                Work work = workRepository
                                .findByIdAndUser_Id(workId, userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Work not found"));

                return toDto(work);

        }

        @Transactional
        public List<WorkResponseDto> getWorksWithFilters(String composer, String technique) {
                UUID userId = userContext.getCurrentUserId();
                String safeComposer = (composer == null || composer.isBlank()) ? "" : composer;

                return workRepository
                                .findByUserIdAndOptionalFilters(userId, safeComposer, technique)
                                .stream()
                                .map(this::toDto)
                                .toList();
        }

        @Transactional
        public WorkResponseDto updateWork(UUID workId, WorkUpdateRequestDto request) {
                UUID userId = userContext.getCurrentUserId();

                Work work = workRepository
                                .findByIdAndUser_Id(workId, userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Work not found"));

                if (request.getTitle() != null)
                        work.setTitle(request.getTitle());
                if (request.getComposer() != null)
                        work.setComposer(request.getComposer());
                if (request.getDifficulty() != null)
                        work.setDifficulty(Difficulty.fromString(request.getDifficulty()));
                if (request.getStatus() != null)
                        work.setStatus(Status.fromString(request.getStatus()));

                return toDto(workRepository.save(work));
        }

        @Transactional
        public void deleteWork(UUID workId) {
                UUID userId = userContext.getCurrentUserId();
                workRepository.deleteByIdAndUser_Id(workId, userId);
        }

        @Transactional
        public WorkResponseDto addTechniqueToWork(UUID workId, String techniqueName) {
                UUID userId = userContext.getCurrentUserId();
                Work work = workRepository
                                .findByIdAndUser_Id(workId, userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Work not found"));

                Technique technique = techniqueRepository.findByName(techniqueName)
                                .orElseThrow(() -> new ResourceNotFoundException("Technique not found"));

                work.getTechniques().add(technique);
                return toDto(workRepository.save(work));
        }

        @Transactional
        public WorkResponseDto removeTechniqueFromWork(UUID workId, UUID techniqueId) {
                UUID userId = userContext.getCurrentUserId();
                Work work = workRepository
                                .findByIdAndUser_Id(workId, userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Work not found"));

                Technique technique = techniqueRepository.findById(techniqueId)
                                .orElseThrow(() -> new ResourceNotFoundException("Technique not found"));

                work.getTechniques().remove(technique);
                return toDto(workRepository.save(work));
        }

        private WorkResponseDto toDto(Work work) {
                SheetResponseDto sheetDto = null;

                if (work.getSheet() != null) {
                        Sheet sheet = work.getSheet();

                        sheetDto = switch (sheet.getType()) {
                                case PDF -> new SheetResponseDto(
                                                sheet.getId(),
                                                SheetType.PDF,
                                                sheet.getPdfFilename(),
                                                null);

                                case IMAGES -> new SheetResponseDto(
                                                sheet.getId(),
                                                SheetType.IMAGES,
                                                null,
                                                sheet.getPages().stream()
                                                                .map(p -> new SheetPageResponseDto(
                                                                                p.getId(),
                                                                                p.getPageOrder()))
                                                                .toList());
                        };
                }

                return new WorkResponseDto(
                                work.getId(),
                                work.getTitle(),
                                work.getComposer(),
                                work.getDifficulty().name(),
                                work.getStatus().name(),
                                work.getTechniques().stream().map(Technique::getName).toList(),
                                sheetDto);
        }

}
