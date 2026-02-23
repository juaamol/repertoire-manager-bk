-- ==========================================
-- GENERAL TECHNIQUES (Root level)
-- ==========================================
INSERT INTO techniques (name, instrumentation_id) VALUES
('Legato', (SELECT id FROM instrumentation WHERE name = 'Any Instrument')),
('Staccato', (SELECT id FROM instrumentation WHERE name = 'Any Instrument')),
('Marcato', (SELECT id FROM instrumentation WHERE name = 'Any Instrument')),
('Tenuto', (SELECT id FROM instrumentation WHERE name = 'Any Instrument')),
('Glissando', (SELECT id FROM instrumentation WHERE name = 'Any Instrument')),
('Trills', (SELECT id FROM instrumentation WHERE name = 'Any Instrument')),
('Tremolo', (SELECT id FROM instrumentation WHERE name = 'Any Instrument')),
('Sforzando (sfz)', (SELECT id FROM instrumentation WHERE name = 'Any Instrument'));

-- ==========================================
-- STRINGS
-- ==========================================
INSERT INTO techniques (name, instrumentation_id) VALUES
('Vibrato', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Spiccato', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Détaché', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Double Stops', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Triple/Quadruple Stops', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Pizzicato', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Bartók Pizzicato (Snap)', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Col Legno', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Sul Ponticello', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Sul Tasto', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Harmonics (Natural)', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Harmonics (Artificial)', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Ricochet / Jeté', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Portato / Louré', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Sautillé', (SELECT id FROM instrumentation WHERE name = 'Strings')),
('Martelé', (SELECT id FROM instrumentation WHERE name = 'Strings'));

-- ==========================================
-- WINDS (Woodwinds & Brass)
-- ==========================================
-- Shared Wind techniques
INSERT INTO techniques (name, instrumentation_id) VALUES
('Double Tonguing', (SELECT id FROM instrumentation WHERE name = 'Winds')),
('Triple Tonguing', (SELECT id FROM instrumentation WHERE name = 'Winds')),
('Flutter Tonguing', (SELECT id FROM instrumentation WHERE name = 'Winds')),
('Circular Breathing', (SELECT id FROM instrumentation WHERE name = 'Winds')),
('Multiphonics', (SELECT id FROM instrumentation WHERE name = 'Winds'));

-- Brass specific
INSERT INTO techniques (name, instrumentation_id) VALUES
('Muted (Con Sordino)', (SELECT id FROM instrumentation WHERE name = 'Brass')),
('Stopped Horn', (SELECT id FROM instrumentation WHERE name = 'Brass')),
('Growl', (SELECT id FROM instrumentation WHERE name = 'Brass'));

-- ==========================================
-- KEYBOARDS
-- ==========================================
INSERT INTO techniques (name, instrumentation_id) VALUES
('Pedal Technique', (SELECT id FROM instrumentation WHERE name = 'Keyboards')),
('Sostenuto Pedal', (SELECT id FROM instrumentation WHERE name = 'Keyboards')),
('Soft Pedal (Una Corda)', (SELECT id FROM instrumentation WHERE name = 'Keyboards')),
('Octave Technique', (SELECT id FROM instrumentation WHERE name = 'Keyboards')),
('Finger Independence', (SELECT id FROM instrumentation WHERE name = 'Keyboards')),
('Finger Substitution', (SELECT id FROM instrumentation WHERE name = 'Keyboards')),
('Cross-hand Playing', (SELECT id FROM instrumentation WHERE name = 'Keyboards')),
('Ornaments (Mordents/Turns)', (SELECT id FROM instrumentation WHERE name = 'Keyboards')),
('Voicing (Inner Voices)', (SELECT id FROM instrumentation WHERE name = 'Keyboards'));

-- ==========================================
-- VOICE
-- ==========================================
INSERT INTO techniques (name, instrumentation_id) VALUES
('Breath Support', (SELECT id FROM instrumentation WHERE name = 'Voice')),
('Coloratura / Agility', (SELECT id FROM instrumentation WHERE name = 'Voice')),
('Vocal Runs', (SELECT id FROM instrumentation WHERE name = 'Voice')),
('Falsetto / Head Voice', (SELECT id FROM instrumentation WHERE name = 'Voice')),
('Belting', (SELECT id FROM instrumentation WHERE name = 'Voice')),
('Mixed Voice', (SELECT id FROM instrumentation WHERE name = 'Voice')),
('Diction / Enunciation', (SELECT id FROM instrumentation WHERE name = 'Voice')),
('Vocal Distortion (Fry/Growl)', (SELECT id FROM instrumentation WHERE name = 'Voice')),
('Messa di Voce', (SELECT id FROM instrumentation WHERE name = 'Voice'));

-- ==========================================
-- PERCUSSION & ELECTRONICS
-- ==========================================
INSERT INTO techniques (name, instrumentation_id) VALUES
('Rolls', (SELECT id FROM instrumentation WHERE name = 'Percussion')),
('Mallet Technique (2/4 Mallets)', (SELECT id FROM instrumentation WHERE name = 'Percussion')),
('Dead Strokes', (SELECT id FROM instrumentation WHERE name = 'Percussion')),
('Rim Shots', (SELECT id FROM instrumentation WHERE name = 'Percussion')),
('Bowed Percussion', (SELECT id FROM instrumentation WHERE name = 'Percussion')),
('Electronic Triggering', (SELECT id FROM instrumentation WHERE name = 'Electronics'));