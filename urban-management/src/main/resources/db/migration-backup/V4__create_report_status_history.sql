CREATE TABLE report_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    report_id UUID NOT NULL,

    from_status VARCHAR(50),
    to_status VARCHAR(50),

    changed_by VARCHAR(255),
    note TEXT,

    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_rsh_report
        FOREIGN KEY (report_id)
        REFERENCES reports(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_rsh_report_id ON report_status_history(report_id);

CREATE INDEX idx_rsh_changed_at ON report_status_history(changed_at);