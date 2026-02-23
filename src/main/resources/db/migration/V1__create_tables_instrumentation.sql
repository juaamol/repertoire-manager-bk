-- ==========================================
-- THE HIERARCHICAL TREE (Lookups)
-- ==========================================

CREATE TABLE IF NOT EXISTS instrumentation (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL UNIQUE,
    parent_id UUID REFERENCES instrumentation(id),
    level INTEGER NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uq_only_one_root
ON instrumentation(parent_id)
WHERE parent_id IS NULL;
