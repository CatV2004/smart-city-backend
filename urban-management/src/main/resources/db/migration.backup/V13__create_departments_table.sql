-- ===============================
-- Table: departments
-- ===============================

CREATE TABLE departments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    name VARCHAR(100) NOT NULL UNIQUE,
    code VARCHAR(50) NOT NULL UNIQUE,

    description TEXT,

    is_active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

-- ===============================
-- Indexes
-- ===============================

-- Tìm theo code
CREATE INDEX idx_departments_code
ON departments(code);

-- Filter theo trạng thái hoạt động
CREATE INDEX idx_departments_active
ON departments(is_active);

-- Soft delete support
CREATE INDEX idx_departments_deleted_at
ON departments(deleted_at);

-- ===============================
-- Trigger for updated_at
-- ===============================

CREATE TRIGGER trg_departments_updated_at
BEFORE UPDATE ON departments
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();