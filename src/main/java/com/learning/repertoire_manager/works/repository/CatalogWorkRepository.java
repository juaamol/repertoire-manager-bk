package com.learning.repertoire_manager.works.repository;

import com.learning.repertoire_manager.works.model.CatalogWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CatalogWorkRepository extends JpaRepository<CatalogWork, UUID> {

  Optional<CatalogWork> findById(UUID workId, UUID userId);

  @Query("""
          SELECT DISTINCT work
          FROM CatalogWork work
          LEFT JOIN work.composer composer
          LEFT JOIN work.instrumentations workInstRelation
          LEFT JOIN workInstRelation.instrumentation instrument
          WHERE (
              :composerName IS NULL OR
              LOWER(composer.name) LIKE LOWER(CONCAT('%', CAST(:composerName AS string), '%'))
            )
            AND (:instrumentName IS NULL OR instrument.name = :instrumentName)
      """)
  Page<CatalogWork> findByFilters(
      @Param("composerName") String composerName,
      @Param("instrumentName") String instrumentName,
      Pageable pageable);

  @Query(value = "SELECT * FROM search_catalog_works(:comp, :title)", countQuery = "SELECT count(*) FROM search_catalog_works(:comp, :title)", nativeQuery = true)
  Page<CatalogWork> search(
      @Param("comp") String composer,
      @Param("title") String titleSubtitle,
      Pageable pageable);
}