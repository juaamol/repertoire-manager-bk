package com.learning.repertoire_manager.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.repertoire_manager.works.model.Sheet;

import java.util.Optional;
import java.util.UUID;

public interface SheetRepository extends JpaRepository<Sheet, UUID> {

    Optional<Sheet> findByPiece_Id(UUID pieceId);

    void deleteByPiece_Id(UUID pieceId);

    Optional<Sheet> findByPiece_IdAndPiece_User_Id(UUID pieceId, UUID userId);

}
