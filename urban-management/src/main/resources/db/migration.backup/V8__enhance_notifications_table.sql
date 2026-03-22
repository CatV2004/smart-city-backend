-- 1. notification type
ALTER TABLE notifications
ADD COLUMN type VARCHAR(50) NOT NULL DEFAULT 'SYSTEM';

-- 2. reference id (ex: report id)
ALTER TABLE notifications
ADD COLUMN reference_id UUID NULL;

-- 3. metadata JSON (Postgres)
ALTER TABLE notifications
ADD COLUMN metadata JSONB NULL;

-- 5. index unread notifications
CREATE INDEX idx_notifications_user_unread
ON notifications (user_id)
WHERE is_read = FALSE;