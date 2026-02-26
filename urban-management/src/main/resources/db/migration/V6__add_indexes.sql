-- REPORTS
CREATE INDEX idx_reports_status_created
ON reports(status, created_at DESC);

CREATE INDEX idx_reports_created_at
ON reports(created_at DESC);

-- dùng PostGIS
CREATE INDEX idx_reports_location
ON reports
USING GIST (location);

-- NOTIFICATIONS
CREATE INDEX idx_notifications_user_created
ON notifications(user_id, created_at DESC);

CREATE INDEX idx_notifications_unread
ON notifications(user_id, is_read);

-- ATTACHMENTS
CREATE INDEX idx_attachments_report
ON attachments(report_id);

