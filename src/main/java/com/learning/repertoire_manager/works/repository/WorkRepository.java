package com.learning.repertoire_manager.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.learning.repertoire_manager.works.model.Work;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkRepository extends JpaRepository<Work, UUID> {
    Optional<Work> findByIdAndUser_Id(UUID workId, UUID userId);

    void deleteByIdAndUser_Id(UUID workId, UUID userId);

    @Query("""
                SELECT p
                FROM Work p
                JOIN p.techniques t
                WHERE p.user.id = :userId
                  AND (:composer IS NULL OR LOWER(p.composer) LIKE LOWER(CONCAT('%', :composer, '%')))
                  AND (:technique IS NULL OR t.name = :technique)
            """)
    List<Work> findByUserIdAndOptionalFilters(
            @Param("userId") UUID userId,
            @Param("composer") String composer,
            @Param("technique") String technique);
}
