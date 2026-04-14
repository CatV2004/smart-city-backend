-- ===============================
-- Tasks table
-- ===============================
CREATE TABLE tasks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    report_id UUID NOT NULL REFERENCES reports(id) ON DELETE CASCADE,
    assigned_user_id UUID REFERENCES users(id) ON DELETE SET NULL,
    status VARCHAR(50),
    note TEXT,
    assigned_at TIMESTAMP,
    started_at TIMESTAMP,
    completed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP NULL
);

CREATE INDEX idx_tasks_assigned_user_id ON tasks(assigned_user_id);
CREATE INDEX idx_tasks_status ON tasks(status);
CREATE INDEX idx_tasks_assigned_at ON tasks(assigned_at);
CREATE INDEX idx_tasks_report_id ON tasks(report_id);

-- ===============================
-- Report Status History table
-- ===============================
CREATE TABLE report_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    report_id UUID NOT NULL REFERENCES reports(id) ON DELETE CASCADE,
    from_status VARCHAR(50),
    to_status VARCHAR(50),
    changed_by VARCHAR(255),
    note TEXT,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_rsh_report_id ON report_status_history(report_id);
CREATE INDEX idx_rsh_changed_at ON report_status_history(changed_at);

-- ===============================
-- Department Offices table
-- ===============================
CREATE TABLE department_offices (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    department_id UUID NOT NULL REFERENCES departments(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    address TEXT,
    location GEOMETRY(Point, 4326) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE INDEX idx_office_department ON department_offices(department_id);
CREATE INDEX idx_office_location ON department_offices USING GIST (location);
ALTER TABLE department_offices ADD CONSTRAINT uq_department_office_name UNIQUE (department_id, name);

-- ===============================
-- Task Evidences table
-- ===============================
CREATE TABLE task_evidences (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    task_id UUID NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    file_url TEXT NOT NULL,
    file_name VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_task_evidences_task_id ON task_evidences(task_id);