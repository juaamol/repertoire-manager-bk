-- ==========================================
-- GLOBAL CATALOG (Read-Only Templates)
-- ==========================================

CREATE TABLE IF NOT EXISTS catalog_composer (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    short_name VARCHAR(255),
    epoch VARCHAR(100),
    birth DATE,
    death DATE
);

CREATE TABLE IF NOT EXISTS catalog_work (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    composer_id UUID NOT NULL REFERENCES catalog_composer(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    classification VARCHAR(255) -- For excerpts, subtitles, ...
);

CREATE TABLE IF NOT EXISTS catalog_number (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    work_id UUID NOT NULL REFERENCES catalog_work(id) ON DELETE CASCADE,
    value VARCHAR(50) NOT NULL,
    display_value TEXT GENERATED ALWAYS AS (replace(value, ':', ' No.')) STORED,
    UNIQUE(work_id, value)
);

-- "settings" array in the data JSON
CREATE TABLE IF NOT EXISTS work_setting (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    work_id UUID NOT NULL REFERENCES catalog_work(id) ON DELETE CASCADE,
    name VARCHAR(255) DEFAULT 'Standard' -- e.g., "Original Version", "Chamber Arrangement"
);

-- The "instrumentation slots" within a "setting"
CREATE TABLE IF NOT EXISTS instrumentation_slot (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    work_setting_id UUID NOT NULL REFERENCES work_setting(id) ON DELETE CASCADE
);

-- The "instrumentations" to fit in a slot of a setting in piece that offers options
-- (i.e. Violin and (Flute or Piccolo))
CREATE TABLE IF NOT EXISTS instrumentation_alternative (
    instrumentation_slot_id UUID NOT NULL REFERENCES instrumentation_slot(id) ON DELETE CASCADE,
    instrumentation_id UUID NOT NULL REFERENCES instrumentation(id),
    quantity INTEGER DEFAULT 1,
    PRIMARY KEY(instrumentation_slot_id, instrumentation_id, quantity)
);
