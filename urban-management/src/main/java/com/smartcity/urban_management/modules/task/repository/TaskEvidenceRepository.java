package com.smartcity.urban_management.modules.task.repository;

import com.smartcity.urban_management.modules.task.entity.TaskEvidence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskEvidenceRepository extends JpaRepository<TaskEvidence, UUID> {
    List<TaskEvidence> findByTaskId(UUID taskId);
}
