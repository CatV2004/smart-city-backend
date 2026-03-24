-- Enable PostGIS
CREATE EXTENSION IF NOT EXISTS postgis;

-- ========================================
-- 1. Create department_offices table
-- ========================================
CREATE TABLE department_offices (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    department_id UUID NOT NULL,

    name VARCHAR(255) NOT NULL,
    address TEXT,

    location GEOMETRY(Point, 4326) NOT NULL,

    is_active BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_office_department
        FOREIGN KEY (department_id)
        REFERENCES departments(id)
        ON DELETE CASCADE
);

-- ========================================
-- 2. Indexes
-- ========================================

-- Index for department
CREATE INDEX idx_office_department
ON department_offices(department_id);

-- Spatial index
CREATE INDEX idx_office_location
ON department_offices
USING GIST (location);

-- Optional: unique name within department
ALTER TABLE department_offices
ADD CONSTRAINT uq_department_office_name
UNIQUE (department_id, name);

-- ========================================
-- 3. Add office_id to users
-- ========================================
ALTER TABLE users
ADD COLUMN office_id UUID;

-- FK constraint
ALTER TABLE users
ADD CONSTRAINT fk_user_office
FOREIGN KEY (office_id)
REFERENCES department_offices(id)
ON DELETE SET NULL;

-- Index for performance
CREATE INDEX idx_user_office
ON users(office_id);