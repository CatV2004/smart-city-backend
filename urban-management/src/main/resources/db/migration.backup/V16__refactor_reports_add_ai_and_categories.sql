-- ===============================
-- Add new columns
-- ===============================

ALTER TABLE reports
ADD COLUMN user_category_id UUID,
ADD COLUMN ai_category_id UUID,
ADD COLUMN final_category_id UUID,
ADD COLUMN ai_confidence DOUBLE PRECISION,
ADD COLUMN priority VARCHAR(20);

-- ===============================
-- Migrate old data
-- ===============================

-- category cũ → user_category + final_category
UPDATE reports
SET
    user_category_id = category_id,
    final_category_id = category_id
WHERE category_id IS NOT NULL;

-- ===============================
-- Set NOT NULL nếu cần (optional)
-- ===============================

-- Nếu bạn chắc chắn luôn có:
-- ALTER TABLE reports
-- ALTER COLUMN user_category_id SET NOT NULL;

-- ===============================
-- Add foreign keys
-- ===============================

ALTER TABLE reports
ADD CONSTRAINT fk_reports_user_category
FOREIGN KEY (user_category_id)
REFERENCES categories(id)
ON DELETE SET NULL;

ALTER TABLE reports
ADD CONSTRAINT fk_reports_ai_category
FOREIGN KEY (ai_category_id)
REFERENCES categories(id)
ON DELETE SET NULL;

ALTER TABLE reports
ADD CONSTRAINT fk_reports_final_category
FOREIGN KEY (final_category_id)
REFERENCES categories(id)
ON DELETE SET NULL;

-- ===============================
-- Indexes
-- ===============================

CREATE INDEX idx_reports_user_category_id
ON reports(user_category_id);

CREATE INDEX idx_reports_ai_category_id
ON reports(ai_category_id);

CREATE INDEX idx_reports_final_category_id
ON reports(final_category_id);

CREATE INDEX idx_reports_priority
ON reports(priority);

-- ===============================
-- Drop old column
-- ===============================

ALTER TABLE reports
DROP COLUMN category_id;