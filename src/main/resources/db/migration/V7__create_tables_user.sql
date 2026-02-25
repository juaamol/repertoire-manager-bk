-- ==========================================
-- USER DATA & PERSONAL REPERTOIRE
-- ==========================================

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Only for composers created by the user
CREATE TABLE IF NOT EXISTS user_composer (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL DEFAULT 'Unknown',
    short_name VARCHAR(255),
    epoch VARCHAR(100),
    birth DATE,
    death DATE,
    UNIQUE(user_id, name)
);

CREATE TABLE IF NOT EXISTS user_work (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    
    -- Composer source (One must be NOT NULL)
    catalog_composer_id UUID REFERENCES catalog_composer(id),
    user_composer_id UUID REFERENCES user_composer(id),
    
    -- Editable fields
    title VARCHAR(255) NOT NULL,
    classification VARCHAR(255),
    notes VARCHAR(255),
    difficulty VARCHAR(50),
    status VARCHAR(50),
    catalog_numbers JSONB DEFAULT '[]', 
    
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT composer_presence CHECK (catalog_composer_id IS NOT NULL OR user_composer_id IS NOT NULL)
);

-- Editable instrumentation for the user's specific copy
CREATE TABLE IF NOT EXISTS user_work_instrumentation (
    work_id UUID NOT NULL REFERENCES user_work(id) ON DELETE CASCADE,
    instrumentation_id UUID NOT NULL REFERENCES instrumentation(id),
    quantity INTEGER DEFAULT 1,
    PRIMARY KEY (work_id, instrumentation_id)
);

-- User's techniques associated with their specific work
CREATE TABLE IF NOT EXISTS user_work_techniques (
    work_id UUID NOT NULL REFERENCES user_work(id) ON DELETE CASCADE,
    technique_id UUID NOT NULL REFERENCES techniques(id) ON DELETE CASCADE,
    PRIMARY KEY (work_id, technique_id)
);
