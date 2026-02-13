INSERT INTO techniques (name) VALUES
-- Common/General Articulations
('Legato'),
('Staccato'),
('Marcato'),
('Tenuto'),
('Glissando'),
('Trills'),
('Tremolo'),
('Sforzando (sfz)'),

-- Strings (Violin, Viola, Cello, Bass)
('Vibrato'),
('Spiccato'),
('Détaché'),
('Double Stops'),
('Triple/Quadruple Stops'),
('Pizzicato'),
('Bartók Pizzicato (Snap)'),
('Col Legno'),
('Sul Ponticello'),
('Sul Tasto'),
('Harmonics (Natural)'),
('Harmonics (Artificial)'),
('Ricochet / Jeté'),
('Portato / Louré'),
('Sautillé'),
('Martelé'),

-- Woodwinds & Brass
('Double Tonguing'),
('Triple Tonguing'),
('Flutter Tonguing'),
('Circular Breathing'),
('Multiphonics'),
('Muted (Con Sordino)'),
('Stopped Horn'),
('Growl'),

-- Keyboard (Piano, Organ)
('Pedal Technique'),
('Sostenuto Pedal'),
('Soft Pedal (Una Corda)'),
('Octave Technique'),
('Finger Independence'),
('Finger Substitution'),
('Cross-hand Playing'),
('Ornaments (Mordents/Turns)'),
('Voicing (Inner Voices)'),

-- Vocal (Soprano, Chorus, Ensemble)
('Breath Support'),
('Coloratura / Agility'),
('Vocal Runs'),
('Falsetto / Head Voice'),
('Belting'),
('Mixed Voice'),
('Diction / Enunciation'),
('Vocal Distortion (Fry/Growl)'),
('Messa di Voce'),

-- Percussion & Modern
('Rolls'),
('Mallet Technique (2/4 Mallets)'),
('Dead Strokes'),
('Rim Shots'),
('Bowed Percussion'),
('Electronic Triggering'),
('Extended Techniques (General)')
ON CONFLICT (name) DO NOTHING;