package com.smartcity.urban_management.modules.task.entity;

import com.smartcity.urban_management.modules.department.entity.Department;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
