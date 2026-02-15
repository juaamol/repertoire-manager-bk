INSERT INTO categories (name) VALUES 
('General'),
('Strings'),
('Woodwinds'),
('Brass'),
('Keyboard'),
('Vocal'),
('Percussion'),
('Electronic'),
('Ensemble'),
('Mechanical'),
('Traditional'),
('Other')
ON CONFLICT (name) DO NOTHING;