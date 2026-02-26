CREATE TABLE reports (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,

    category VARCHAR(100),
    status VARCHAR(50) DEFAULT 'PENDING',

    location GEOGRAPHY(Point, 4326) NOT NULL,
    address TEXT,

    created_by UUID REFERENCES users(id),

    approved_by UUID REFERENCES users(id),

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP NULL
);