package com.learning.repertoire_manager.works.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learning.repertoire_manager.works.model.Instrumentation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstrumentationRepository extends JpaRepository<Instrumentation, UUID> {
  Optional<Instrumentation> findById(UUID id);

  @Query(value = """
      WITH RECURSIVE
      search_anchor AS (
          SELECT id, name, parent_id, level
          FROM instrumentation
          WHERE name ILIKE '%' || :query || '%'
      ),
      parents AS (
          SELECT id, name, parent_id, level FROM search_anchor
          UNION
          SELECT i.id, i.name, i.parent_id, i.level
          FROM instrumentation i
          JOIN parents p ON i.id = p.parent_id
      ),
      children AS (
          SELECT id, name, parent_id, level FROM search_anchor
          UNION
          SELECT i.id, i.name, i.parent_id, i.level
          FROM instrumentation i
          JOIN children c ON i.parent_id = c.id
      )
      SELECT DISTINCT id, name, parent_id, level
      FROM (
          SELECT * FROM parents
          UNION
          SELECT * FROM children
      ) AS full_branch
      ORDER BY level ASC, name ASC
      """, nativeQuery = true)
  List<Instrumentation> findFullBranchByName(@Param("query") String query);
}