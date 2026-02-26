CREATE TABLE notifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id UUID REFERENCES users(id),

    title VARCHAR(255),
    content TEXT,

    is_read BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT NOW(),
    read_at TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL
);