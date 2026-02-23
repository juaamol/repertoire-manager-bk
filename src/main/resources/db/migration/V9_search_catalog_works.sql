-- Ensure Extensions are active
CREATE EXTENSION IF NOT EXISTS unaccent SCHEMA public;
CREATE EXTENSION IF NOT EXISTS pg_trgm SCHEMA public;

-- Helper Function
CREATE OR REPLACE FUNCTION f_unaccent(text) RETURNS text AS $$
    SELECT public.unaccent($1);
$$ LANGUAGE sql IMMUTABLE;

-- Index for Composer
CREATE INDEX IF NOT EXISTS idx_catalog_composers_name_trgm 
ON catalog_composers USING gin (f_unaccent(name) gin_trgm_ops);

-- Index for Work Title
CREATE INDEX IF NOT EXISTS idx_catalog_works_title_trgm 
ON catalog_works USING gin (f_unaccent(title) gin_trgm_ops);

-- Index for Work Subtitle (Mirroring the coalesce used in the query)
CREATE INDEX IF NOT EXISTS idx_catalog_works_subtitle_trgm 
ON catalog_works USING gin (f_unaccent(coalesce(subtitle, '')) gin_trgm_ops);

-- The Search Function
CREATE OR REPLACE FUNCTION search_catalog_works(
    p_composer_query TEXT, 
    p_title_query TEXT
)
RETURNS SETOF catalog_works AS $$
BEGIN
    RETURN QUERY
    SELECT w.*
    FROM catalog_works w
    JOIN catalog_composers c ON w.composer_id = c.id
    WHERE 
        -- COMPOSER CATEGORY
        (p_composer_query IS NULL OR p_composer_query = '' OR (
            f_unaccent(c.name) % f_unaccent(p_composer_query) -- Fuzzy (Vivaldo -> Vivaldi)
            OR 
            f_unaccent(c.name) ILIKE f_unaccent('%' || p_composer_query || '%') -- Substring (V -> Vivaldi)
        ))
        
        AND -- Only works by that composer

        -- TITLE CATEGORY
        (p_title_query IS NULL OR p_title_query = '' OR (
            f_unaccent(w.title) % f_unaccent(p_title_query) -- Fuzzy Title
            OR 
            f_unaccent(w.title) ILIKE f_unaccent('%' || p_title_query || '%') -- Substring Title
            OR 
            f_unaccent(coalesce(w.subtitle, '')) % f_unaccent(p_title_query) -- Fuzzy Subtitle
            OR 
            f_unaccent(coalesce(w.subtitle, '')) ILIKE f_unaccent('%' || p_title_query || '%') -- Substring Subtitle
        ))
    ORDER BY (
        -- Calculate Relevance for Sorting
        similarity(f_unaccent(c.name), f_unaccent(coalesce(p_composer_query, ''))) * 3.0 +
        similarity(f_unaccent(w.title), f_unaccent(coalesce(p_title_query, ''))) * 2.0 +
        similarity(f_unaccent(coalesce(w.subtitle, '')), f_unaccent(coalesce(p_title_query, ''))) * 1.0
    ) DESC;
END;
$$ LANGUAGE plpgsql STABLE;