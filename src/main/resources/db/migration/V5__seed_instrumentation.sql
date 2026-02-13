-- Strings
INSERT INTO instrumentation (name, category_id) VALUES
('Violin', (SELECT id FROM categories WHERE name = 'Strings')),
('Viola', (SELECT id FROM categories WHERE name = 'Strings')),
('Cello', (SELECT id FROM categories WHERE name = 'Strings')),
('Double Bass', (SELECT id FROM categories WHERE name = 'Strings')),
('Guitar', (SELECT id FROM categories WHERE name = 'Strings')),
('Harp', (SELECT id FROM categories WHERE name = 'Strings')),
('Lute', (SELECT id FROM categories WHERE name = 'Strings'))
ON CONFLICT (name) DO NOTHING;

-- Keyboard
INSERT INTO instrumentation (name, category_id) VALUES
('Piano', (SELECT id FROM categories WHERE name = 'Keyboard')),
('Organ', (SELECT id FROM categories WHERE name = 'Keyboard')),
('Harpsichord', (SELECT id FROM categories WHERE name = 'Keyboard')),
('Celesta', (SELECT id FROM categories WHERE name = 'Keyboard')),
('Accordion', (SELECT id FROM categories WHERE name = 'Keyboard'))
ON CONFLICT (name) DO NOTHING;

-- Woodwinds
INSERT INTO instrumentation (name, category_id) VALUES
('Flute', (SELECT id FROM categories WHERE name = 'Woodwinds')),
('Oboe', (SELECT id FROM categories WHERE name = 'Woodwinds')),
('Clarinet', (SELECT id FROM categories WHERE name = 'Woodwinds')),
('Bassoon', (SELECT id FROM categories WHERE name = 'Woodwinds')),
('Saxophone', (SELECT id FROM categories WHERE name = 'Woodwinds'))
ON CONFLICT (name) DO NOTHING;

-- Brass
INSERT INTO instrumentation (name, category_id) VALUES
('Trumpet', (SELECT id FROM categories WHERE name = 'Brass')),
('Horn', (SELECT id FROM categories WHERE name = 'Brass')),
('Trombone', (SELECT id FROM categories WHERE name = 'Brass')),
('Tuba', (SELECT id FROM categories WHERE name = 'Brass'))
ON CONFLICT (name) DO NOTHING;

-- Vocal
INSERT INTO instrumentation (name, category_id) VALUES
('Soprano', (SELECT id FROM categories WHERE name = 'Vocal')),
('Mezzo-Soprano', (SELECT id FROM categories WHERE name = 'Vocal')),
('Tenor', (SELECT id FROM categories WHERE name = 'Vocal')),
('Baritone', (SELECT id FROM categories WHERE name = 'Vocal')),
('Bass', (SELECT id FROM categories WHERE name = 'Vocal')),
('Mixed Chorus', (SELECT id FROM categories WHERE name = 'Vocal'))
ON CONFLICT (name) DO NOTHING;

-- Ensembles
INSERT INTO instrumentation (name, category_id) VALUES
('Symphony Orchestra', (SELECT id FROM categories WHERE name = 'Ensemble')),
('Chamber Orchestra', (SELECT id FROM categories WHERE name = 'Ensemble')),
('String Quartet', (SELECT id FROM categories WHERE name = 'Ensemble'))
ON CONFLICT (name) DO NOTHING;
