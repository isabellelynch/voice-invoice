-- Fictional demo customers only. John and Joan Murphy are deliberately similar,
-- so the demo can show the "which customer did you mean?" ambiguity check.
INSERT INTO customer (name, email, address, created_at) VALUES
    ('John Murphy',  'john.murphy@example.com',  '1 Demo Street, Tralee',   CURRENT_TIMESTAMP),
    ('Joan Murphy',  'joan.murphy@example.com',  '2 Sample Road, Killarney', CURRENT_TIMESTAMP),
    ('Mary O''Brien', NULL,                       '3 Test Lane, Dingle',     CURRENT_TIMESTAMP);
