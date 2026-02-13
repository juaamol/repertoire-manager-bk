package com.learning.repertoire_manager.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.repertoire_manager.works.model.SheetPage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SheetPageRepository extends JpaRepository<SheetPage, UUID> {
    List<SheetPage> findBySheet_IdOrderByPageOrderAsc(UUID sheetId);

    Optional<SheetPage> findByIdAndSheet_Piece_User_Id(UUID pageId, UUID userId);
}
