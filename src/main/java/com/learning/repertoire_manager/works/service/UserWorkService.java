package com.learning.repertoire_manager.works.service;

import com.learning.repertoire_manager.exception.ResourceNotFoundException;
import com.learning.repertoire_manager.security.UserContext;
import com.learning.repertoire_manager.user.model.User;
import com.learning.repertoire_manager.works.dto.*;
import com.learning.repertoire_manager.works.mapper.WorkMapper;
import com.learning.repertoire_manager.works.model.*;
import com.learning.repertoire_manager.works.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserWorkService {
        private final UserWorkRepository userWorkRepository;
        private final CatalogComposerRepository catalogComposerRepository;
        private final UserComposerRepository userComposerRepository;
        private final TechniqueRepository techniqueRepository;
        private final UserContext userContext;
        private final WorkMapper workMapper;

        @Transactional
        public WorkResponseDto createWork(WorkCreateRequestDto request) {
                UUID userId = userContext.getCurrentUserId();

                UserWork work = UserWork.builder()
                                .user(User.builder().id(userId).build())
                                .title(request.getTitle())
                                .subtitle(request.getSubtitle())
                                .notes(request.getNotes())
                                .difficulty(Difficulty.fromString(request.getDifficulty()))
                                .status(Status.fromString(request.getStatus()))
                                .build();

                if (request.getComposerId() != null) {
                        linkExistingComposer(work, request.getComposerId(), userId);
                } else if (request.getComposerName() != null && !request.getComposerName().isBlank()) {
                        findOrCreateUserComposer(work, request.getComposerName(), userId);
                } else {
                        throw new IllegalArgumentException("You must provide either a Composer ID or a Composer Name.");
                }

                if (request.getTechniqueIds() != null)
                        work.setTechniques(findTechniquesByIds(request.getTechniqueIds()));

                return workMapper.toDto(userWorkRepository.save(work));
        }

        @Transactional(readOnly = true)
        public Page<WorkResponseDto> getWorksWithFilters(
                        String composer,
                        String technique,
                        String instrument,
                        String difficulty,
                        String status,
                        Pageable pageable) {

                UUID userId = userContext.getCurrentUserId();
                Difficulty eDifficulty = Difficulty.fromString(difficulty);
                Status eStatus = Status.fromString(status);

                return userWorkRepository
                                .findByFilters(userId, composer, technique, instrument, eDifficulty, eStatus, pageable)
                                .map(workMapper::toDto);
        }

        @Transactional
        public WorkResponseDto updateWork(UUID workId, WorkUpdateRequestDto request) {
                UUID userId = userContext.getCurrentUserId();
                UserWork work = userWorkRepository.findByIdAndUserId(workId, userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Work not found"));

                work.setSubtitle(request.getSubtitle());
                work.setNotes(request.getNotes());

                if (request.getTitle() != null)
                        work.setTitle(request.getTitle());
                if (request.getDifficulty() != null)
                        work.setDifficulty(Difficulty.fromString(request.getDifficulty()));
                if (request.getStatus() != null)
                        work.setStatus(Status.fromString(request.getStatus()));
                if (request.getComposerId() != null)
                        linkExistingComposer(work, request.getComposerId(), userId);
                if (request.getTechniqueIds() != null)
                        work.getTechniques().addAll(findTechniquesByIds(request.getTechniqueIds()));

                return workMapper.toDto(userWorkRepository.save(work));
        }

        @Transactional(readOnly = true)
        public WorkResponseDto getWorkById(UUID workId) {
                UUID userId = userContext.getCurrentUserId();
                UserWork work = userWorkRepository.findByIdAndUserId(workId, userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Work not found"));
                return workMapper.toDto(work);
        }

        @Transactional
        public void deleteWork(UUID workId) {
                UUID userId = userContext.getCurrentUserId();
                userWorkRepository.deleteByIdAndUserId(workId, userId);
        }

        private List<Technique> findTechniquesByIds(List<UUID> ids) {
                return ids.stream()
                                .map(id -> techniqueRepository.findById(id)
                                                .orElseThrow(() -> new ResourceNotFoundException(
                                                                "Technique not found: " + id)))
                                .toList();
        }

        private void linkExistingComposer(UserWork work, UUID composerId, UUID userId) {
                var catalogComposer = catalogComposerRepository.findById(composerId);

                if (catalogComposer.isPresent()) {
                        work.setCatalogComposer(catalogComposer.get());
                        work.setUserComposer(null);
                        return;
                }

                var userComposer = userComposerRepository.findById(composerId)
                                .filter(composer -> composer.getUser().getId().equals(userId))
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Composer ID not found in catalog or your personal list."));

                work.setUserComposer(userComposer);
                work.setCatalogComposer(null);
        }

        private void findOrCreateUserComposer(UserWork work, String composerName, UUID userId) {
                UserComposer composer = userComposerRepository.findByNameAndUserId(composerName, userId)
                                .orElseGet(() -> {
                                        UserComposer newComposer = UserComposer.builder()
                                                        .user(User.builder().id(userId).build())
                                                        .name(composerName)
                                                        .build();
                                        return userComposerRepository.save(newComposer);
                                });

                work.setUserComposer(composer);
                work.setCatalogComposer(null);
        }

        
}