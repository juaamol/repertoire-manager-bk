package com.learning.repertoire_manager.works.service;

import com.learning.repertoire_manager.exception.ResourceNotFoundException;
import com.learning.repertoire_manager.works.dto.*;
import com.learning.repertoire_manager.works.mapper.CatalogWorkMapper;
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
public class CatalogWorkService {
        private final CatalogWorkRepository workRepository;
        private final CatalogWorkMapper workMapper;

        @Transactional(readOnly = true)
        public CatalogWorkResponseDto getWorkById(UUID workId) {
                CatalogWork work = workRepository.findById(workId)
                                .orElseThrow(() -> new ResourceNotFoundException("Work not found"));
                return workMapper.toDto(work);
        }

        @Transactional(readOnly = true)
        public Page<CatalogWorkResponseDto> getWorksWithFilters(
                        String composer,
                        String instrument,
                        Pageable pageable) {

                return workRepository.findByFilters(composer, instrument, pageable).map(workMapper::toDto);
        }

        @Transactional(readOnly = true)
        public Page<CatalogWorkResponseDto> search(String composer, String titleSubtitle, Pageable pageable) {
                return workRepository.search(composer, titleSubtitle, pageable).map(workMapper::toDto);
        }
}