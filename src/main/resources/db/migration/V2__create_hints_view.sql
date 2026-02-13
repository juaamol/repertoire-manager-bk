CREATE OR REPLACE VIEW instrumentation_technique_hints AS
SELECT 
    i.id AS instrumentation_id,
    i.name AS instrumentation_name,
    t.id AS technique_id,
    t.name AS technique_name
FROM instrumentation i
JOIN techniques t ON (
    t.category_id = i.category_id 
    OR 
    t.category_id = (SELECT id FROM categories WHERE name = 'General')
);