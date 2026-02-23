-- ==========================================
-- SEARCH FUNCTION
-- ==========================================

CREATE OR REPLACE FUNCTION search_catalog_composers(
    p_name_query TEXT
)
RETURNS SETOF catalog_composers AS $$
BEGIN
    RETURN QUERY
    SELECT *,
    FROM catalog_composers composers
    WHERE 
        p_name_query IS NOT NULL AND p_name_query != '' AND (
            f_unaccent(composers.name) % f_unaccent(p_name_query)
            OR f_unaccent(composers.name) ILIKE f_unaccent('%' || p_name_query || '%')
            OR f_unaccent(composers.short_name) ILIKE f_unaccent('%' || p_name_query || '%')
        )
    ORDER BY
        (
            similarity(f_unaccent(composers.name), f_unaccent(p_name_query)) * 2.0 + 
            similarity(f_unaccent(composers.short_name), f_unaccent(p_name_query))
        ) DESC,
        composers.name ASC;
END;
$$ LANGUAGE plpgsql STABLE;