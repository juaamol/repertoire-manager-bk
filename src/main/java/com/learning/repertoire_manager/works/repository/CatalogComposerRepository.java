package com.learning.repertoire_manager.works.repository;

import com.learning.repertoire_manager.works.model.CatalogComposer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CatalogComposerRepository extends JpaRepository<CatalogComposer, UUID> {
    @Query("""
          SELECT DISTINCT composer
          FROM CatalogComposer composer
          WHERE :name IS NULL OR LOWER(composer.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%'))
      """)
    Page<CatalogComposer> findByName(@Param("name") String name, Pageable pageable);
}