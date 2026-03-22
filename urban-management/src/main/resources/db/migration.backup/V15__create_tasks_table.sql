-- ===============================
-- Table: tasks
-- ===============================

CREATE TABLE tasks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    report_id UUID NOT NULL,
    department_id UUID NOT NULL,
    assigned_user_id UUID,

    status VARCHAR(50),

    note TEXT,

    assigned_at TIMESTAMP,
    started_at TIMESTAMP,
    completed_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

-- ===============================
-- Constraints
-- ===============================

-- 1 report chỉ có 1 task
ALTER TABLE tasks
ADD CONSTRAINT uq_tasks_report UNIQUE (report_id);

-- ===============================
-- Foreign Keys
-- ===============================

ALTER TABLE tasks
ADD CONSTRAINT fk_tasks_report
FOREIGN KEY (report_id)
REFERENCES reports(id)
ON DELETE CASCADE;

ALTER TABLE tasks
ADD CONSTRAINT fk_tasks_department
FOREIGN KEY (department_id)
REFERENCES departments(id)
ON DELETE RESTRICT;

ALTER TABLE tasks
ADD CONSTRAINT fk_tasks_user
FOREIGN KEY (assigned_user_id)
REFERENCES users(id)
ON DELETE SET NULL;

-- ===============================
-- Indexes
-- ===============================

-- Query theo department (rất nhiều)
CREATE INDEX idx_tasks_department_id
ON tasks(department_id);

-- Query theo assigned user
CREATE INDEX idx_tasks_assigned_user_id
ON tasks(assigned_user_id);

-- Query theo status
CREATE INDEX idx_tasks_status
ON tasks(status);

-- Query theo thời gian
CREATE INDEX idx_tasks_assigned_at
ON tasks(assigned_at);

-- ===============================
-- Trigger for updated_at
-- ===============================

CREATE TRIGGER trg_tasks_updated_at
BEFORE UPDATE ON tasks
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();