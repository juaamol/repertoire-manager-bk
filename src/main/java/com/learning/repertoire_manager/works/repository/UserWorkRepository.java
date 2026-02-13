package com.learning.repertoire_manager.works.repository;

import com.learning.repertoire_manager.works.model.Status;
import com.learning.repertoire_manager.works.model.Difficulty;
import com.learning.repertoire_manager.works.model.UserWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserWorkRepository extends JpaRepository<UserWork, UUID> {

  Optional<UserWork> findByIdAndUserId(UUID workId, UUID userId);

  void deleteByIdAndUserId(UUID workId, UUID userId);

  @Query("""
          SELECT DISTINCT work
          FROM UserWork work
          LEFT JOIN work.catalogComposer catComposer
          LEFT JOIN work.userComposer customComposer
          LEFT JOIN work.techniques technique
          LEFT JOIN work.instrumentations workInstRelation
          LEFT JOIN workInstRelation.instrumentation instrument
          WHERE work.user.id = :userId
            AND (
              :composerName IS NULL OR
              LOWER(catComposer.name) LIKE LOWER(CONCAT('%', :composerName, '%')) OR
              LOWER(customComposer.name) LIKE LOWER(CONCAT('%', :composerName, '%'))
            )
            AND (:techniqueName IS NULL OR technique.name = :techniqueName)
            AND (:instrumentName IS NULL OR instrument.name = :instrumentName)
            AND (:difficulty IS NULL OR work.difficulty = :difficulty)
            AND (:status IS NULL OR work.status = :status)
      """)
  Page<UserWork> findByFilters(
      @Param("userId") UUID userId,
      @Param("composerName") String composerName,
      @Param("techniqueName") String techniqueName,
      @Param("instrumentName") String instrumentName,
      @Param("difficulty") Difficulty difficulty,
      @Param("status") Status status,
      Pageable pageable);
}