CREATE TABLE IF NOT EXISTS techniques (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    instrumentation_id UUID REFERENCES instrumentation(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    UNIQUE(instrumentation_id, name)
);