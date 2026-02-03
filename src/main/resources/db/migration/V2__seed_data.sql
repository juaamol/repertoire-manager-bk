-- === USERS ===
INSERT INTO users (email, password_hash) VALUES
('violin@test.com', 'fake-hash')
ON CONFLICT (email) DO NOTHING;

-- === TECHNIQUES ===
INSERT INTO techniques (name) VALUES
('Vibrato'),
('Spiccato'),
('Legato'),
('Staccato'),
('Double Stop')
ON CONFLICT (name) DO NOTHING;

-- === PIECES ===
-- Violin Concerto in A minor (Bach)
INSERT INTO pieces (user_id, title, composer, difficulty, status)
SELECT id, 'Violin Concerto in A minor', 'Bach', 'INTERMEDIATE', 'LEARNING'
FROM users
WHERE email='violin@test.com'
ON CONFLICT (user_id, title) DO NOTHING;

-- Air Varie (Oscar Rieding, Op. 23, No. 3)
INSERT INTO pieces (user_id, title, composer, difficulty, status)
SELECT id, 'Air Varie', 'Oscar Rieding, Op. 23, No. 3', 'INTERMEDIATE', 'LEARNING'
FROM users
WHERE email='violin@test.com'
ON CONFLICT (user_id, title) DO NOTHING;

-- Air (Bach)
INSERT INTO pieces (user_id, title, composer, difficulty, status)
SELECT id, 'Air', 'Bach', 'INTERMEDIATE', 'LEARNING'
FROM users
WHERE email='violin@test.com'
ON CONFLICT (user_id, title) DO NOTHING;

-- Canon in D (Pachelbel)
INSERT INTO pieces (user_id, title, composer, difficulty, status)
SELECT id, 'Canon in D', 'Pachelbel', 'INTERMEDIATE', 'LEARNING'
FROM users
WHERE email='violin@test.com'
ON CONFLICT (user_id, title) DO NOTHING;

-- === PIECE_TECHNIQUES ===
-- Violin Concerto in A minor -> Vibrato, Spiccato
INSERT INTO piece_techniques (piece_id, technique_id)
SELECT p.id, t.id
FROM pieces p, techniques t, users u
WHERE p.title='Violin Concerto in A minor' 
  AND u.email='violin@test.com'
  AND p.user_id=u.id
  AND t.name IN ('Vibrato', 'Spiccato')
ON CONFLICT DO NOTHING;

-- Air Varie -> Legato, Vibrato
INSERT INTO piece_techniques (piece_id, technique_id)
SELECT p.id, t.id
FROM pieces p, techniques t, users u
WHERE p.title='Air Varie' 
  AND u.email='violin@test.com'
  AND p.user_id=u.id
  AND t.name IN ('Legato', 'Vibrato')
ON CONFLICT DO NOTHING;

-- Air (Bach) -> Legato, Vibrato
INSERT INTO piece_techniques (piece_id, technique_id)
SELECT p.id, t.id
FROM pieces p, techniques t, users u
WHERE p.title='Air' 
  AND u.email='violin@test.com'
  AND p.user_id=u.id
  AND t.name IN ('Legato', 'Vibrato')
ON CONFLICT DO NOTHING;

-- Canon in D -> Legato, Staccato, Double Stop
INSERT INTO piece_techniques (piece_id, technique_id)
SELECT p.id, t.id
FROM pieces p, techniques t, users u
WHERE p.title='Canon in D' 
  AND u.email='violin@test.com'
  AND p.user_id=u.id
  AND t.name IN ('Legato', 'Staccato', 'Double Stop')
ON CONFLICT DO NOTHING;
