-- ==================================
-- Add category reference to reports
-- ==================================

ALTER TABLE reports
ADD COLUMN category_id UUID;

ALTER TABLE reports
ADD CONSTRAINT fk_reports_category
FOREIGN KEY (category_id)
REFERENCES categories(id);

CREATE INDEX idx_reports_category_id
ON reports(category_id);