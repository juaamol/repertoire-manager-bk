package com.learning.repertoire_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.repertoire_manager.model.User;
import com.learning.repertoire_manager.model.Piece;

import java.util.List;
import java.util.UUID;

public interface PieceRepository extends JpaRepository<Piece, UUID> {
    List<Piece> findByUser(User user);
}