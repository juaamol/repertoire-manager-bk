-- General Articulations
INSERT INTO techniques (name, category_id) VALUES
('Legato', (SELECT id FROM categories WHERE name = 'General')),
('Staccato', (SELECT id FROM categories WHERE name = 'General')),
('Marcato', (SELECT id FROM categories WHERE name = 'General')),
('Tenuto', (SELECT id FROM categories WHERE name = 'General')),
('Glissando', (SELECT id FROM categories WHERE name = 'General')),
('Trills', (SELECT id FROM categories WHERE name = 'General')),
('Tremolo', (SELECT id FROM categories WHERE name = 'General')),
('Sforzando (sfz)', (SELECT id FROM categories WHERE name = 'General'))
ON CONFLICT (name) DO NOTHING;

-- Strings
INSERT INTO techniques (name, category_id) VALUES
('Vibrato', (SELECT id FROM categories WHERE name = 'Strings')),
('Spiccato', (SELECT id FROM categories WHERE name = 'Strings')),
('Détaché', (SELECT id FROM categories WHERE name = 'Strings')),
('Double Stops', (SELECT id FROM categories WHERE name = 'Strings')),
('Triple/Quadruple Stops', (SELECT id FROM categories WHERE name = 'Strings')),
('Pizzicato', (SELECT id FROM categories WHERE name = 'Strings')),
('Bartók Pizzicato (Snap)', (SELECT id FROM categories WHERE name = 'Strings')),
('Col Legno', (SELECT id FROM categories WHERE name = 'Strings')),
('Sul Ponticello', (SELECT id FROM categories WHERE name = 'Strings')),
('Sul Tasto', (SELECT id FROM categories WHERE name = 'Strings')),
('Harmonics (Natural)', (SELECT id FROM categories WHERE name = 'Strings')),
('Harmonics (Artificial)', (SELECT id FROM categories WHERE name = 'Strings')),
('Ricochet / Jeté', (SELECT id FROM categories WHERE name = 'Strings')),
('Portato / Louré', (SELECT id FROM categories WHERE name = 'Strings')),
('Sautillé', (SELECT id FROM categories WHERE name = 'Strings')),
('Martelé', (SELECT id FROM categories WHERE name = 'Strings'))
ON CONFLICT (name) DO NOTHING;

-- Woodwinds & Brass (Mapped to Woodwinds for logic, can be shared)
INSERT INTO techniques (name, category_id) VALUES
('Double Tonguing', (SELECT id FROM categories WHERE name = 'Woodwinds')),
('Triple Tonguing', (SELECT id FROM categories WHERE name = 'Woodwinds')),
('Flutter Tonguing', (SELECT id FROM categories WHERE name = 'Woodwinds')),
('Circular Breathing', (SELECT id FROM categories WHERE name = 'Woodwinds')),
('Multiphonics', (SELECT id FROM categories WHERE name = 'Woodwinds')),
('Muted (Con Sordino)', (SELECT id FROM categories WHERE name = 'Brass')),
('Stopped Horn', (SELECT id FROM categories WHERE name = 'Brass')),
('Growl', (SELECT id FROM categories WHERE name = 'Brass'))
ON CONFLICT (name) DO NOTHING;

-- Keyboard
INSERT INTO techniques (name, category_id) VALUES
('Pedal Technique', (SELECT id FROM categories WHERE name = 'Keyboard')),
('Sostenuto Pedal', (SELECT id FROM categories WHERE name = 'Keyboard')),
('Soft Pedal (Una Corda)', (SELECT id FROM categories WHERE name = 'Keyboard')),
('Octave Technique', (SELECT id FROM categories WHERE name = 'Keyboard')),
('Finger Independence', (SELECT id FROM categories WHERE name = 'Keyboard')),
('Finger Substitution', (SELECT id FROM categories WHERE name = 'Keyboard')),
('Cross-hand Playing', (SELECT id FROM categories WHERE name = 'Keyboard')),
('Ornaments (Mordents/Turns)', (SELECT id FROM categories WHERE name = 'Keyboard')),
('Voicing (Inner Voices)', (SELECT id FROM categories WHERE name = 'Keyboard'))
ON CONFLICT (name) DO NOTHING;

-- Vocal
INSERT INTO techniques (name, category_id) VALUES
('Breath Support', (SELECT id FROM categories WHERE name = 'Vocal')),
('Coloratura / Agility', (SELECT id FROM categories WHERE name = 'Vocal')),
('Vocal Runs', (SELECT id FROM categories WHERE name = 'Vocal')),
('Falsetto / Head Voice', (SELECT id FROM categories WHERE name = 'Vocal')),
('Belting', (SELECT id FROM categories WHERE name = 'Vocal')),
('Mixed Voice', (SELECT id FROM categories WHERE name = 'Vocal')),
('Diction / Enunciation', (SELECT id FROM categories WHERE name = 'Vocal')),
('Vocal Distortion (Fry/Growl)', (SELECT id FROM categories WHERE name = 'Vocal')),
('Messa di Voce', (SELECT id FROM categories WHERE name = 'Vocal'))
ON CONFLICT (name) DO NOTHING;

-- Percussion & Modern
INSERT INTO techniques (name, category_id) VALUES
('Rolls', (SELECT id FROM categories WHERE name = 'Percussion')),
('Mallet Technique (2/4 Mallets)', (SELECT id FROM categories WHERE name = 'Percussion')),
('Dead Strokes', (SELECT id FROM categories WHERE name = 'Percussion')),
('Rim Shots', (SELECT id FROM categories WHERE name = 'Percussion')),
('Bowed Percussion', (SELECT id FROM categories WHERE name = 'Percussion')),
('Electronic Triggering', (SELECT id FROM categories WHERE name = 'Electronic'))
ON CONFLICT (name) DO NOTHING;
