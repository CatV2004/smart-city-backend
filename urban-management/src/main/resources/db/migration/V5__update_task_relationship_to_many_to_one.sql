-- 1. Drop UNIQUE constraint (OneToOne → ManyToOne)
ALTER TABLE tasks
DROP CONSTRAINT IF EXISTS tasks_report_id_key;

-- 2. (Optional nhưng nên làm) tạo index cho performance
CREATE INDEX IF NOT EXISTS idx_tasks_report_id ON tasks(report_id);