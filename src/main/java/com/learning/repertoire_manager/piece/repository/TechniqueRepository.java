package com.learning.repertoire_manager.piece.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.repertoire_manager.piece.model.Technique;

import java.util.Optional;
import java.util.UUID;

public interface TechniqueRepository extends JpaRepository<Technique, UUID> {
    Optional<Technique> findByName(String name);
}