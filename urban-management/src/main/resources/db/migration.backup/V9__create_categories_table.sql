-- ===============================
-- Create categories table
-- ===============================

CREATE TABLE categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    name VARCHAR(255) NOT NULL UNIQUE,
    slug VARCHAR(255) NOT NULL UNIQUE,

    description TEXT,

    icon VARCHAR(100),
    color VARCHAR(50),

    ai_class VARCHAR(100),

    is_active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

-- ===============================
-- Indexes
-- ===============================

CREATE INDEX idx_categories_slug
ON categories(slug);

CREATE INDEX idx_categories_ai_class
ON categories(ai_class);

CREATE INDEX idx_categories_active
ON categories(is_active);

-- ===============================
-- Trigger for updated_at
-- ===============================

CREATE TRIGGER trg_categories_updated_at
BEFORE UPDATE ON categories
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();