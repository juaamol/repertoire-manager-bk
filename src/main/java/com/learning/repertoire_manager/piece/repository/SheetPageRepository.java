package com.learning.repertoire_manager.piece.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.learning.repertoire_manager.piece.model.SheetPage;

import java.util.UUID;

public interface SheetPageRepository extends JpaRepository<SheetPage, UUID> {
}
