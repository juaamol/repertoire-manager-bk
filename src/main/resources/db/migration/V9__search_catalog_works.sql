CREATE EXTENSION IF NOT EXISTS unaccent;
CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE OR REPLACE FUNCTION f_unaccent(text) RETURNS text AS $$
    SELECT public.unaccent($1);
$$ LANGUAGE sql IMMUTABLE;

-- ==========================================
-- SEARCH INDEXES
-- ==========================================

-- Fuzzy Search Indexes (GIN Trigram) -> for % and ILIKE
CREATE INDEX IF NOT EXISTS idx_composers_name_trgm 
ON catalog_composer USING gin (f_unaccent(name) gin_trgm_ops);

CREATE INDEX IF NOT EXISTS idx_works_title_trgm 
ON catalog_work USING gin (f_unaccent(title) gin_trgm_ops);

CREATE INDEX IF NOT EXISTS idx_works_class_trgm 
ON catalog_work USING gin (f_unaccent(COALESCE(classification, '')) gin_trgm_ops);

CREATE INDEX IF NOT EXISTS idx_identifiers_display_value_trgm 
ON catalog_number USING gin (f_unaccent(display_value) gin_trgm_ops);

-- B-Tree Indexes for Filters
CREATE INDEX IF NOT EXISTS idx_works_composer_id ON catalog_work(composer_id);
CREATE INDEX IF NOT EXISTS idx_settings_work_id ON work_setting(work_id);
CREATE INDEX IF NOT EXISTS idx_slot_work_setting_id ON instrumentation_slot(work_setting_id);
CREATE INDEX IF NOT EXISTS idx_alternative_instrumentation_slot_id ON instrumentation_alternative(instrumentation_slot_id);
CREATE INDEX IF NOT EXISTS idx_alternative_instrumentation_id ON instrumentation_alternative(instrumentation_id);
CREATE INDEX IF NOT EXISTS idx_instrumentation_parent_id ON instrumentation(parent_id);

-- ==========================================
-- SEARCH FUNCTION
-- ==========================================
-- 1. Global search (Title, Classification, Catalog Numbers)
-- 2. Fixed Composer filtering (Dropdown)
-- 3. Hierarchical Instrument filtering (Dropdown - e.g. picking "Winds" finds "Flute")
-- 4. Relevance-based sorting
-- ==========================================

CREATE OR REPLACE FUNCTION search_catalog_work(
    p_query TEXT, 
    p_composer_id UUID DEFAULT NULL, 
    p_instrument_ids UUID[] DEFAULT NULL
)
RETURNS TABLE (
    id UUID,
    title VARCHAR,
    classification VARCHAR,
    composer_id UUID,
    composer_name VARCHAR,
    catalog_display TEXT,
    relevance REAL
) AS $$
DECLARE
    v_query TEXT := f_unaccent(trim(COALESCE(p_query, '')));
    v_ts_query tsquery := websearch_to_tsquery('simple', v_query);
BEGIN
    RETURN QUERY
    WITH RECURSIVE target_instruments AS (
        SELECT i.id 
        FROM instrumentation i 
        WHERE i.id = ANY(p_instrument_ids)
        
        UNION ALL
        
        SELECT i.id 
        FROM instrumentation i 
        JOIN target_instruments ti ON i.parent_id = ti.id 
    ),
    work_ids AS (
        SELECT work_id, string_agg(display_value, ' | ') as all_values
        FROM catalog_number 
        GROUP BY work_id
    )
    SELECT 
        w.id, 
        w.title, 
        w.classification, 
        c.id as composer_id,
        c.name as composer_name, 
        COALESCE(ids.all_values, '') as catalog_display,
        (
            -- Exact Title Match
            (CASE WHEN f_unaccent(w.title) ILIKE v_query THEN 10.0 ELSE 0.0 END) +

            -- Full-Text Rank
            (ts_rank_cd(to_tsvector('simple', f_unaccent(w.title)), v_ts_query) * 8.0) +

            -- Identifier Match
            (similarity(v_query, f_unaccent(COALESCE(ids.all_values, ''))) * 7.0) +

            -- Title Word Similarity
            (word_similarity(v_query, f_unaccent(w.title)) * 6.0) +

            -- Classification Similarity
            (word_similarity(v_query, f_unaccent(COALESCE(w.classification, ''))) * 3.0) +

            -- Trigram Similarity
            (similarity(v_query, f_unaccent(w.title)) * 2.0)
        )::REAL AS relevance
    FROM catalog_work w
    JOIN catalog_composer c ON w.composer_id = c.id
    LEFT JOIN work_ids ids ON w.id = ids.work_id
    WHERE 
        (p_composer_id IS NULL OR w.composer_id = p_composer_id)
        
        AND
        
        (p_instrument_ids IS NULL OR cardinality(p_instrument_ids) = 0 OR EXISTS (
            SELECT 1 FROM work_setting setting
            JOIN instrumentation_slot slot ON setting.id = slot.work_setting_id
            JOIN instrumentation_alternative alternative ON alternative.instrumentation_slot_id = slot.id
            WHERE setting.work_id = w.id 
            AND alternative.instrumentation_id IN (SELECT ti.id FROM target_instruments ti)
        ))

        AND

        (v_query = '' OR (
            v_query <% f_unaccent(w.title)
            OR to_tsvector('simple', f_unaccent(w.title || ' ' || COALESCE(w.classification, ''))) @@ v_ts_query
            OR f_unaccent(COALESCE(w.classification, '')) % v_query
            OR f_unaccent(ids.all_values) ILIKE '%' || v_query || '%'
            OR v_query % f_unaccent(ids.all_values)
        ))
    ORDER BY 
        relevance DESC, 
        LENGTH(w.title) ASC,
        w.title ASC;
END;
$$ LANGUAGE plpgsql STABLE;
