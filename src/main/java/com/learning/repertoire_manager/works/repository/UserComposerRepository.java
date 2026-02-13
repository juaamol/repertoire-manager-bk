package com.learning.repertoire_manager.works.repository;

import com.learning.repertoire_manager.works.model.UserComposer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface UserComposerRepository extends JpaRepository<UserComposer, UUID> {
    List<UserComposer> findByUserId(UUID userId);
    Optional<UserComposer> findByNameAndUserId(String name, UUID userId);
}