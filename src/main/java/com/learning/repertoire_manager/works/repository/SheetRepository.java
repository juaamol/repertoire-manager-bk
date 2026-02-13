package com.learning.repertoire_manager.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.repertoire_manager.works.model.Sheet;

import java.util.Optional;
import java.util.UUID;

public interface SheetRepository extends JpaRepository<Sheet, UUID> {

    Optional<Sheet> findByWork_Id(UUID workId);

    void deleteByWork_Id(UUID workId);

    Optional<Sheet> findByWork_IdAndWork_User_Id(UUID workId, UUID userId);

}
