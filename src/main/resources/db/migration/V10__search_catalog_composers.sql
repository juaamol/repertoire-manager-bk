-- ==========================================
-- SEARCH FUNCTION
-- ==========================================

CREATE OR REPLACE FUNCTION search_catalog_composer(
    p_name_query TEXT
)
RETURNS SETOF catalog_composer AS $$
DECLARE
    v_name_query TEXT := f_unaccent(p_name_query);
BEGIN
    RETURN QUERY
    SELECT *
    FROM catalog_composer composers
    WHERE 
        p_name_query IS NOT NULL AND p_name_query != '' AND (
            f_unaccent(composers.name) % v_name_query
            OR f_unaccent(composers.name) ILIKE '%' || v_name_query || '%'
            OR f_unaccent(composers.short_name) % v_name_query
            OR f_unaccent(composers.short_name) ILIKE '%' || v_name_query || '%'
        )
    ORDER BY
        (
            -- TIER 1: Exact Matches (Highest Priority)
            (CASE WHEN f_unaccent(composers.name) ILIKE v_name_query THEN 100.0 ELSE 0.0 END) +
            (CASE WHEN f_unaccent(composers.short_name) ILIKE v_name_query THEN 90.0 ELSE 0.0 END) +
            
            -- TIER 2: Prefix Matches (High Priority)
            (CASE WHEN f_unaccent(composers.name) ILIKE v_name_query || '%' THEN 50.0 ELSE 0.0 END) +
            (CASE WHEN f_unaccent(composers.short_name) ILIKE v_name_query || '%' THEN 40.0 ELSE 0.0 END) +
            
            -- TIER 3: Word Similarity (Medium Priority)
            (word_similarity(v_name_query, f_unaccent(composers.name)) * 20.0) +
            (word_similarity(v_name_query, f_unaccent(composers.short_name)) * 10.0) +
            
            -- TIER 4: Standard Trigram Similarity. For typos (Low Priority)
            (similarity(v_name_query, f_unaccent(composers.name)) * 5.0) +
            (similarity(v_name_query, f_unaccent(composers.short_name)) * 2.5)
        ) DESC,
        LENGTH(composers.name) ASC,
        composers.name ASC;
END;
$$ LANGUAGE plpgsql STABLE;