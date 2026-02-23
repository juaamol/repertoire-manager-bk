package com.learning.repertoire_manager.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.learning.repertoire_manager.works.model.Instrumentation;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstrumentationRepository extends JpaRepository<Instrumentation, UUID> {
  Optional<Instrumentation> findById(UUID id);

  @Query("""
          SELECT DISTINCT instrumentation
          FROM Instrumentation instrumentation
          WHERE (
              :name IS NOT NULL AND
              LOWER(instrumentation.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%'))
            )
      """)
  Page<Instrumentation> findByName(
      @Param("name") String name,
      Pageable pageable);
}