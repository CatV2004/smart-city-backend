-- ===============================
-- Add department_id to categories
-- ===============================

ALTER TABLE categories
ADD COLUMN department_id UUID;

UPDATE categories
SET department_id = (SELECT id FROM departments WHERE code = 'TRANSPORT')
WHERE department_id IS NULL;

ALTER TABLE categories
ALTER COLUMN department_id SET NOT NULL;

-- Add foreign key
ALTER TABLE categories
ADD CONSTRAINT fk_categories_department
FOREIGN KEY (department_id)
REFERENCES departments(id)
ON DELETE RESTRICT;

-- Index
CREATE INDEX idx_categories_department_id
ON categories(department_id);

-- ===============================
-- Add department_id to users
-- ===============================

ALTER TABLE users
ADD COLUMN department_id UUID;

-- Add foreign key
ALTER TABLE users
ADD CONSTRAINT fk_users_department
FOREIGN KEY (department_id)
REFERENCES departments(id)
ON DELETE SET NULL;

-- Index
CREATE INDEX idx_users_department_id
ON users(department_id);