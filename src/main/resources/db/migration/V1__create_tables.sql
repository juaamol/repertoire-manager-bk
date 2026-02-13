-- ==========================================
-- SHARED LOOKUP TABLES
-- ==========================================

-- Master list of instrument names (e.g., "Piano", "Violin", "Symphony Orchestra")
CREATE TABLE IF NOT EXISTS instrumentation (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Master list of techniques
CREATE TABLE IF NOT EXISTS techniques (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE
);

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
    subtitle VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS catalog_work_instrumentation (
    work_id UUID NOT NULL REFERENCES catalog_works(id) ON DELETE CASCADE,
    instrumentation_id UUID NOT NULL REFERENCES instrumentation(id),
    rank VARCHAR(50) NOT NULL DEFAULT '', -- e.g., "I", "II", "Solo"
    quantity INTEGER DEFAULT 1,
    PRIMARY KEY (work_id, instrumentation_id, rank)
);

-- ==========================================
-- USER DATA & PERSONAL REPERTOIRE
-- ==========================================

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Only for composers created manually by the user
CREATE TABLE IF NOT EXISTS user_composers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    short_name VARCHAR(255),
    epoch VARCHAR(100),
    birth DATE,
    death DATE,
    UNIQUE(user_id, name)
);

CREATE TABLE IF NOT EXISTS user_works (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    
    -- Composer source (One must be NOT NULL)
    catalog_composer_id UUID REFERENCES catalog_composers(id),
    user_composer_id UUID REFERENCES user_composers(id),
    
    -- Editable fields
    title VARCHAR(255) NOT NULL,
    subtitle VARCHAR(255),
    notes VARCHAR(255),
    difficulty VARCHAR(50),
    status VARCHAR(50),
    
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT composer_presence CHECK (catalog_composer_id IS NOT NULL OR user_composer_id IS NOT NULL)
);

-- Editable instrumentation for the user's specific copy
CREATE TABLE IF NOT EXISTS user_work_instrumentation (
    user_work_id UUID NOT NULL REFERENCES user_works(id) ON DELETE CASCADE,
    instrumentation_id UUID NOT NULL REFERENCES instrumentation(id),
    rank VARCHAR(50) NOT NULL DEFAULT '',
    quantity INTEGER DEFAULT 1,
    PRIMARY KEY (user_work_id, instrumentation_id, rank)
);

-- User's techniques associated with their specific work
CREATE TABLE IF NOT EXISTS user_work_techniques (
    user_work_id UUID NOT NULL REFERENCES user_works(id) ON DELETE CASCADE,
    technique_id UUID NOT NULL REFERENCES techniques(id) ON DELETE CASCADE,
    PRIMARY KEY (user_work_id, technique_id)
);
