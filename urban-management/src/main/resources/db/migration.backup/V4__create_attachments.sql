CREATE TABLE attachments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    report_id UUID NOT NULL
        REFERENCES reports(id) ON DELETE CASCADE,

    file_name VARCHAR(255),
    file_url TEXT NOT NULL,

    storage_provider VARCHAR(50) DEFAULT 'cloudinary',
    public_id VARCHAR(255),     -- id file trên cloudinary

    file_type VARCHAR(50),      -- image/jpeg, video/mp4
    file_size INTEGER,          -- bytes

    created_at TIMESTAMP DEFAULT NOW(),
    deleted_at TIMESTAMP NULL
);