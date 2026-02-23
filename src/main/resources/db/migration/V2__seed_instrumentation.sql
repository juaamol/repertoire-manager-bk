-- Create helper function to handle the "Parent ID + Parent Level + 1" logic
CREATE OR REPLACE FUNCTION add_instrumentation(instr_name TEXT, p_name TEXT) 
RETURNS VOID AS $$
BEGIN
    INSERT INTO instrumentation (name, parent_id, level)
    SELECT instr_name, id, level + 1
    FROM instrumentation
    WHERE name = p_name;
END;
$$ LANGUAGE plpgsql;

-- Root (Level 0)
INSERT INTO instrumentation (name, parent_id, level) VALUES ('Any Instrument', NULL, 0);

SELECT add_instrumentation('Wind Instrument', 'Any Instrument');
SELECT add_instrumentation('Winds', 'Wind Instrument');
SELECT add_instrumentation('Strings', 'Any Instrument');
SELECT add_instrumentation('Keyboards', 'Any Instrument');
SELECT add_instrumentation('Percussion', 'Any Instrument');
SELECT add_instrumentation('Voice', 'Any Instrument');
SELECT add_instrumentation('Electronics', 'Any Instrument');
SELECT add_instrumentation('Orchestra', 'Any Instrument');
SELECT add_instrumentation('Chamber Ensemble', 'Any Instrument');
SELECT add_instrumentation('Melody Instrument', 'Any Instrument');
SELECT add_instrumentation('Treble Instrument', 'Any Instrument');
SELECT add_instrumentation('Airplane Propeller', 'Any Instrument');
SELECT add_instrumentation('Helicopter', 'Any Instrument');
SELECT add_instrumentation('Amplified Plant', 'Any Instrument');
SELECT add_instrumentation('Zoomoozophone', 'Any Instrument');

-- Winds
SELECT add_instrumentation('Brass', 'Winds');
SELECT add_instrumentation('Flute', 'Winds');
SELECT add_instrumentation('Oboe', 'Winds');
SELECT add_instrumentation('Clarinet', 'Winds');
SELECT add_instrumentation('Bassoon', 'Winds');
SELECT add_instrumentation('Saxophone', 'Winds');
SELECT add_instrumentation('Recorder', 'Winds');
SELECT add_instrumentation('Wind Ensemble', 'Winds');
-- Strings
SELECT add_instrumentation('Violin', 'Strings');
SELECT add_instrumentation('Viola', 'Strings');
SELECT add_instrumentation('Cello', 'Strings');
SELECT add_instrumentation('Double Bass', 'Strings');
SELECT add_instrumentation('Guitar', 'Strings');
SELECT add_instrumentation('Lute', 'Strings');
SELECT add_instrumentation('Harp', 'Strings');
SELECT add_instrumentation('Viol', 'Strings');
SELECT add_instrumentation('String Ensemble', 'Strings');
-- Keyboards
SELECT add_instrumentation('Piano', 'Keyboards');
SELECT add_instrumentation('Organ', 'Keyboards');
SELECT add_instrumentation('Harpsichord', 'Keyboards');
SELECT add_instrumentation('Accordion', 'Keyboards');
-- Voice
SELECT add_instrumentation('High Voice', 'Voice');
SELECT add_instrumentation('Medium Voice', 'Voice');
SELECT add_instrumentation('Chorus', 'Voice');
SELECT add_instrumentation('Vocal Ensemble', 'Voice');
SELECT add_instrumentation('Narrator', 'Voice');
SELECT add_instrumentation('Female Voice', 'Voice');
SELECT add_instrumentation('Male Voice', 'Voice');
-- Orchestra / Ensemble
SELECT add_instrumentation('Baroque Orchestra', 'Orchestra');
SELECT add_instrumentation('Chamber Orchestra', 'Orchestra');
SELECT add_instrumentation('Theater Orchestra','Orchestra');
SELECT add_instrumentation('Gagaku Ensemble', 'Orchestra');
SELECT add_instrumentation('Gamelan Ensemble', 'Orchestra');
SELECT add_instrumentation('Instrumental Ensemble', 'Chamber Ensemble');
SELECT add_instrumentation('Mixed Consort', 'Chamber Ensemble');
SELECT add_instrumentation('Basso Continuo', 'Chamber Ensemble');
SELECT add_instrumentation('Concertino', 'Chamber Ensemble');
SELECT add_instrumentation('Jazz Band', 'Any Instrument');
SELECT add_instrumentation('Rock Band', 'Any Instrument');

-- Brass
SELECT add_instrumentation('Trumpet', 'Brass');
SELECT add_instrumentation('Horn', 'Brass');
SELECT add_instrumentation('Trombone', 'Brass');
SELECT add_instrumentation('Tuba', 'Brass');
SELECT add_instrumentation('Brass Ensemble', 'Brass');
SELECT add_instrumentation('Brass Band', 'Brass');
SELECT add_instrumentation('Military Band', 'Brass');
-- Woodwinds
SELECT add_instrumentation('Piccolo', 'Flute');
SELECT add_instrumentation('Alto Flute', 'Flute');
SELECT add_instrumentation('Bass Flute', 'Flute');
SELECT add_instrumentation('Oboe d''amore', 'Oboe');
SELECT add_instrumentation('English Horn', 'Oboe');
SELECT add_instrumentation('Bass Clarinet', 'Clarinet');
SELECT add_instrumentation('Basset Horn', 'Clarinet');
SELECT add_instrumentation('Contrabassoon', 'Bassoon');
SELECT add_instrumentation('Soprano Saxophone', 'Saxophone');
SELECT add_instrumentation('Alto Saxophone', 'Saxophone');
SELECT add_instrumentation('Tenor Saxophone', 'Saxophone');
SELECT add_instrumentation('Baritone Saxophone', 'Saxophone');
SELECT add_instrumentation('Saxophone Quartet', 'Saxophone');
-- Strings
SELECT add_instrumentation('Violino Piccolo', 'Violin');
SELECT add_instrumentation('Viola d''amore', 'Viola');
SELECT add_instrumentation('Violone', 'Double Bass');
SELECT add_instrumentation('Viol Ensemble', 'Viol');
SELECT add_instrumentation('Bass Viol', 'Viol');
SELECT add_instrumentation('Viola da gamba', 'Viol');
SELECT add_instrumentation('Theorbo', 'Lute');
SELECT add_instrumentation('Chittarone', 'Lute');
SELECT add_instrumentation('Electric Guitar', 'Guitar');
SELECT add_instrumentation('Bass Guitar', 'Guitar');
-- Keyboards
SELECT add_instrumentation('Prepared Piano', 'Piano');
SELECT add_instrumentation('Clavichord', 'Keyboards');
SELECT add_instrumentation('Spinet', 'Keyboards');
SELECT add_instrumentation('Electric Organ', 'Organ');
SELECT add_instrumentation('Harmonium', 'Organ');
SELECT add_instrumentation('Bandoneon', 'Accordion');
SELECT add_instrumentation('Bayan', 'Accordion');
-- Voice
SELECT add_instrumentation('Soprano', 'High Voice');
SELECT add_instrumentation('Mezzo-Soprano', 'High Voice');
SELECT add_instrumentation('Tenor', 'Voice');
SELECT add_instrumentation('Baritone', 'Medium Voice');
SELECT add_instrumentation('Alto (Voice)', 'Medium Voice');
SELECT add_instrumentation('Bass (Voice)', 'Voice');
SELECT add_instrumentation('Children''s Chorus', 'Chorus');
SELECT add_instrumentation('Double Chorus', 'Chorus');
-- Percussion
SELECT add_instrumentation('Timpani', 'Percussion');
SELECT add_instrumentation('Drums', 'Percussion');
SELECT add_instrumentation('Bells', 'Percussion');
SELECT add_instrumentation('Marimba', 'Percussion');
SELECT add_instrumentation('Glockenspiel', 'Percussion');
SELECT add_instrumentation('Vibraphone', 'Percussion');
SELECT add_instrumentation('Percussion Ensemble', 'Percussion');
-- Electronics
SELECT add_instrumentation('Synthesizer', 'Electronics');
SELECT add_instrumentation('Live Electronics', 'Electronics');
SELECT add_instrumentation('Electronic Tape', 'Electronics');
SELECT add_instrumentation('Amplifier', 'Electronics');

-- LEVEL 4: Highly Specific / Variants
SELECT add_instrumentation('Piccolo Trumpet', 'Trumpet');
SELECT add_instrumentation('Cornet', 'Trumpet');
SELECT add_instrumentation('Cornett', 'Trumpet');
SELECT add_instrumentation('Alto Horn', 'Horn');
SELECT add_instrumentation('Euphonium', 'Tuba');
SELECT add_instrumentation('Serpent', 'Tuba');
SELECT add_instrumentation('Amplified Flute', 'Flute');
SELECT add_instrumentation('Amplified Piano', 'Piano');
SELECT add_instrumentation('Player Piano', 'Piano');
SELECT add_instrumentation('Electric Flute', 'Flute');
SELECT add_instrumentation('Heckelphone', 'Oboe');
SELECT add_instrumentation('Chalumeau', 'Clarinet');
SELECT add_instrumentation('Bass Recorder', 'Recorder');
SELECT add_instrumentation('Pipe', 'Recorder');
SELECT add_instrumentation('Electric Violin', 'Violin');
SELECT add_instrumentation('Electric Viola', 'Viola');
SELECT add_instrumentation('Electric Cello', 'Cello');
SELECT add_instrumentation('Electric Double Bass', 'Double Bass');
SELECT add_instrumentation('Viol Consort', 'Viol Ensemble');
SELECT add_instrumentation('Treble Ensemble', 'String Ensemble');
SELECT add_instrumentation('Electric Piano', 'Piano');
SELECT add_instrumentation('Toy Piano', 'Piano');
SELECT add_instrumentation('Piano Ensemble', 'Piano');
SELECT add_instrumentation('Pianola', 'Piano');
SELECT add_instrumentation('Mechanical Clock', 'Organ');
SELECT add_instrumentation('Harpolyre', 'Guitar');
SELECT add_instrumentation('Boy Soprano', 'High Voice');
SELECT add_instrumentation('Treble', 'High Voice');
SELECT add_instrumentation('Countertenor', 'High Voice');
SELECT add_instrumentation('Contralto', 'Medium Voice');
SELECT add_instrumentation('Female Chorus', 'Chorus');
SELECT add_instrumentation('Male Chorus', 'Chorus');
SELECT add_instrumentation('Unison Chorus', 'Chorus');
SELECT add_instrumentation('Voice Ensemble', 'Vocal Ensemble');
SELECT add_instrumentation('Speaker', 'Narrator');
SELECT add_instrumentation('Reciter', 'Narrator');
SELECT add_instrumentation('Male Narrator', 'Narrator');
SELECT add_instrumentation('Assistant', 'Narrator');
SELECT add_instrumentation('Snare Drum', 'Drums');
SELECT add_instrumentation('Bongos', 'Drums');
SELECT add_instrumentation('Drum Kit', 'Drums');
SELECT add_instrumentation('Gong', 'Drums');
SELECT add_instrumentation('Cymbal', 'Drums');
SELECT add_instrumentation('Triangle', 'Drums');
SELECT add_instrumentation('Castanet', 'Drums');
SELECT add_instrumentation('Claves', 'Drums');
SELECT add_instrumentation('Maracas', 'Drums');
SELECT add_instrumentation('Woodblock', 'Drums');
SELECT add_instrumentation('Handclaps', 'Drums');
SELECT add_instrumentation('Tubular Bells', 'Bells');
SELECT add_instrumentation('Carillon', 'Bells');
SELECT add_instrumentation('Music Box', 'Bells');
SELECT add_instrumentation('Electric Bell', 'Bells');
SELECT add_instrumentation('Bells Ensemble', 'Bells');
SELECT add_instrumentation('Digital Sampler', 'Synthesizer');
SELECT add_instrumentation('Ring modulator', 'Synthesizer');
SELECT add_instrumentation('Frequency generator', 'Synthesizer');
SELECT add_instrumentation('Loudspeaker', 'Amplifier');
SELECT add_instrumentation('Microphone', 'Amplifier');
SELECT add_instrumentation('Pickup', 'Amplifier');
-- Remaining instruments
SELECT add_instrumentation('Arpeggione', 'Strings');
SELECT add_instrumentation('Baryton', 'Strings');
SELECT add_instrumentation('Balalaika', 'Strings');
SELECT add_instrumentation('Domra', 'Strings');
SELECT add_instrumentation('Sitar', 'Strings');
SELECT add_instrumentation('Koto', 'Strings');
SELECT add_instrumentation('Pipa', 'Strings');
SELECT add_instrumentation('Biwa', 'Strings');
SELECT add_instrumentation('Kora', 'Strings');
SELECT add_instrumentation('Zither', 'Strings');
SELECT add_instrumentation('Calchedon', 'Lute');
SELECT add_instrumentation('Orpharion', 'Lute');
SELECT add_instrumentation('Mandolin', 'Guitar');
SELECT add_instrumentation('Mandola', 'Guitar');
SELECT add_instrumentation('Harmonica', 'Any Instrument');
SELECT add_instrumentation('Didgeridoo', 'Winds');
SELECT add_instrumentation('Shakuhachi', 'Winds');
SELECT add_instrumentation('Sho', 'Winds');
SELECT add_instrumentation('Bombarde', 'Winds');
SELECT add_instrumentation('Conch Shell', 'Winds');
SELECT add_instrumentation('Celesta', 'Percussion');
SELECT add_instrumentation('Cimbalom', 'Percussion');
SELECT add_instrumentation('Xylorimba', 'Percussion');
SELECT add_instrumentation('Glass Harmonica', 'Percussion');
SELECT add_instrumentation('Ondes Martenot', 'Electronics');
SELECT add_instrumentation('Theremin', 'Electronics');
SELECT add_instrumentation('Turntable', 'Electronics');
SELECT add_instrumentation('Shortwave Receiver', 'Electronics');
SELECT add_instrumentation('Flute-Clock', 'Mechanical Clock');
SELECT add_instrumentation('Clock Organ', 'Organ');
SELECT add_instrumentation('Color Organ', 'Organ');
SELECT add_instrumentation('Lira Organizzata', 'Organ');
SELECT add_instrumentation('Jazz Ensemble', 'Jazz Band');
SELECT add_instrumentation('Synthesized Tape', 'Electronic Tape');

DROP FUNCTION add_instrumentation(TEXT, TEXT);