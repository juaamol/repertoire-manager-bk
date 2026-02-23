-- ==========================================
-- GLOBAL CATALOG (Read-Only Templates)
-- ==========================================

CREATE TABLE IF NOT EXISTS catalog_composers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    short_name VARCHAR(255),
    epoch VARCHAR(100),
    birth DATE,
    death DATE
);

CREATE TABLE IF NOT EXISTS catalog_works (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    composer_id UUID NOT NULL REFERENCES catalog_composers(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    classification VARCHAR(255) -- For excerpts, subtitles, ...
);

CREATE TABLE IF NOT EXISTS catalog_work_identifiers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    work_id UUID NOT NULL REFERENCES catalog_works(id) ON DELETE CASCADE,
    value VARCHAR(50) NOT NULL,
    display_value TEXT GENERATED ALWAYS AS (replace(value, ':', ' No.')) STORED,
    UNIQUE(work_id, value)
);

-- "settings" array in the data JSON
CREATE TABLE IF NOT EXISTS catalog_work_settings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    work_id UUID NOT NULL REFERENCES catalog_works(id) ON DELETE CASCADE,
    name VARCHAR(255) DEFAULT 'Standard' -- e.g., "Original Version", "Chamber Arrangement"
);

-- The "alternatives" within a "setting"
CREATE TABLE IF NOT EXISTS catalog_instrumentation_alternatives (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    setting_id UUID NOT NULL REFERENCES catalog_work_settings(id) ON DELETE CASCADE,
    instrumentation_id UUID NOT NULL REFERENCES instrumentation(id),
    quantity INTEGER DEFAULT 1
);
