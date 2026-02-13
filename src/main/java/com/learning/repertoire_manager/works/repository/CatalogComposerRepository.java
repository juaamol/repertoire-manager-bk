package com.learning.repertoire_manager.works.repository;

import com.learning.repertoire_manager.works.model.CatalogComposer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CatalogComposerRepository extends JpaRepository<CatalogComposer, UUID> {
}