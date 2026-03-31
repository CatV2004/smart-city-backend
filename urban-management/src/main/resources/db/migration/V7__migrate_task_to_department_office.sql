-- 1. Add new column (NOT NULL luôn vì không có data)
ALTER TABLE tasks
ADD COLUMN department_office_id UUID NOT NULL;

-- 2. Add foreign key
ALTER TABLE tasks
ADD CONSTRAINT fk_tasks_department_office
FOREIGN KEY (department_office_id)
REFERENCES department_offices(id)
ON DELETE RESTRICT;

-- 3. Create index
CREATE INDEX idx_tasks_department_office_id
ON tasks(department_office_id);

-- 4. Drop old index
DROP INDEX IF EXISTS idx_tasks_department_id;

-- 5. Drop old column
ALTER TABLE tasks
DROP COLUMN department_id;