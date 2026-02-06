package com.learning.repertoire_manager.piece.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.learning.repertoire_manager.piece.model.Piece;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PieceRepository extends JpaRepository<Piece, UUID> {
    Optional<Piece> findByIdAndUser_Id(UUID pieceId, UUID userId);

    void deleteByIdAndUser_Id(UUID pieceId, UUID userId);

    @Query("""
                SELECT p
                FROM Piece p
                JOIN p.techniques t
                WHERE p.user.id = :userId
                  AND (:composer IS NULL OR LOWER(p.composer) LIKE LOWER(CONCAT('%', :composer, '%')))
                  AND (:technique IS NULL OR t.name = :technique)
            """)
    List<Piece> findByUserIdAndOptionalFilters(
            @Param("userId") UUID userId,
            @Param("composer") String composer,
            @Param("technique") String technique);
}
