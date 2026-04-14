CREATE TABLE task_evidences (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    task_id UUID NOT NULL,

    file_url TEXT NOT NULL,
    file_name VARCHAR(255),

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_task_evidences_task
        FOREIGN KEY (task_id)
        REFERENCES tasks(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_task_evidences_task_id
    ON task_evidences(task_id);