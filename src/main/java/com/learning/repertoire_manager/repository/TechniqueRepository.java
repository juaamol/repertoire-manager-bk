package com.learning.repertoire_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.repertoire_manager.model.Technique;

import java.util.Optional;
import java.util.UUID;

public interface TechniqueRepository extends JpaRepository<Technique, UUID> {
    Optional<Technique> findByName(String name);
}