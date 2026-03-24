package com.smartcity.urban_management.modules.report.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "report_status_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportStatusHistory {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_status")
    private ReportStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_status")
    private ReportStatus toStatus;

    @Column(name = "changed_by")
    private String changedBy;

    private String note;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;
}
