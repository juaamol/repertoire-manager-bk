package com.learning.repertoire_manager.repository;

import com.learning.repertoire_manager.model.Piece;
import com.learning.repertoire_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PieceRepository extends JpaRepository<Piece, UUID> {
    List<Piece> findByUser(User user);

    List<Piece> findByUserAndComposerContainingIgnoreCase(User user, String composer);

    @Query("SELECT p FROM Piece p JOIN p.techniques t WHERE p.user = :user AND t.name = :technique")
    List<Piece> findByUserAndTechnique(@Param("user") User user, @Param("technique") String technique);

    @Query("SELECT p FROM Piece p JOIN p.techniques t WHERE p.user = :user AND (:composer IS NULL OR LOWER(p.composer) LIKE LOWER(CONCAT('%', :composer, '%'))) AND (:technique IS NULL OR t.name = :technique)")
    List<Piece> findByUserAndOptionalFilters(
        @Param("user") User user,
        @Param("composer") String composer,
        @Param("technique") String technique
    );
}
