package com.learning.repertoire_manager.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.repertoire_manager.works.model.Instrumentation;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstrumentationRepository extends JpaRepository<Instrumentation, UUID> {
  Optional<Instrumentation> findById(UUID id);
}