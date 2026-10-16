-- Seed of the professional categories (idempotent: safe to replay on every startup).
INSERT INTO categories (name, slug) VALUES
    ('Alimentation', 'alimentation'),
    ('Bâtiment',     'batiment'),
    ('Fabrication',  'fabrication'),
    ('Services',     'services')
ON CONFLICT (slug) DO NOTHING;
