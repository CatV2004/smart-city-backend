-- ===============================
-- Add office_id to users
-- ===============================
ALTER TABLE users
ADD COLUMN office_id UUID REFERENCES department_offices(id) ON DELETE SET NULL;

CREATE INDEX idx_user_office ON users(office_id);

-- ===============================
-- Add department_office_id to tasks
-- ===============================
ALTER TABLE tasks
ADD COLUMN department_office_id UUID NOT NULL REFERENCES department_offices(id) ON DELETE RESTRICT;

CREATE INDEX idx_tasks_department_office_id ON tasks(department_office_id);

-- ===============================
-- Triggers for updated_at
-- ===============================
CREATE TRIGGER trg_users_updated_at BEFORE UPDATE ON users FOR EACH ROW EXECUTE FUNCTION set_updated_at();
CREATE TRIGGER trg_reports_updated_at BEFORE UPDATE ON reports FOR EACH ROW EXECUTE FUNCTION set_updated_at();
CREATE TRIGGER trg_categories_updated_at BEFORE UPDATE ON categories FOR EACH ROW EXECUTE FUNCTION set_updated_at();
CREATE TRIGGER trg_departments_updated_at BEFORE UPDATE ON departments FOR EACH ROW EXECUTE FUNCTION set_updated_at();
CREATE TRIGGER trg_tasks_updated_at BEFORE UPDATE ON tasks FOR EACH ROW EXECUTE FUNCTION set_updated_at();
CREATE TRIGGER trg_notifications_updated_at BEFORE UPDATE ON notifications FOR EACH ROW EXECUTE FUNCTION set_updated_at();