package com.learning.repertoire_manager.works.repository;

import com.learning.repertoire_manager.works.model.CatalogComposer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CatalogComposerRepository extends JpaRepository<CatalogComposer, UUID> {
    @Query(value = "SELECT * FROM search_catalog_composers(:name)", countQuery = "SELECT count(*) FROM search_catalog_composers(:name)", nativeQuery = true)
    Page<CatalogComposer> findByName(@Param("name") String name, Pageable pageable);
}