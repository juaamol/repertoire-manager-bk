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

    @Query(value = "SELECT * FROM search_catalog_works(:query, :composerId, CAST(:instrumentIds AS uuid[]))", countQuery = "SELECT count(*) FROM search_catalog_works(:query, :composerId, CAST(:instrumentIds AS uuid[]))", nativeQuery = true)
    Page<CatalogWork> search(
            @Param("query") String query,
            @Param("composerId") UUID composerId,
            @Param("instrumentIds") UUID[] instrumentIds,
            Pageable pageable);
}