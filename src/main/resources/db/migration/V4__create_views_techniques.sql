CREATE OR REPLACE VIEW instrumentation_technique_hints AS
WITH RECURSIVE instrumentation_lineage AS (
    SELECT 
        id AS leaf_id, 
        name AS leaf_name, 
        id AS ancestor_id, 
        parent_id
    FROM instrumentation

    UNION ALL

    SELECT 
        il.leaf_id, 
        il.leaf_name, 
        i.id, 
        i.parent_id
    FROM instrumentation_lineage il
    JOIN instrumentation i ON il.parent_id = i.id
)
SELECT 
    il.leaf_id AS instrumentation_id,
    il.leaf_name AS instrumentation_name,
    t.id AS technique_id,
    t.name AS technique_name,
    i.name AS source_instrumentation_name
FROM instrumentation_lineage il
JOIN techniques t ON t.instrumentation_id = il.ancestor_id
JOIN instrumentation i ON i.id = il.ancestor_id;