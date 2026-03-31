package com.smartcity.urban_management.modules.task.validator;

import com.smartcity.urban_management.modules.task.entity.Task;
import com.smartcity.urban_management.modules.task.entity.TaskStatus;
import com.smartcity.urban_management.modules.task.repository.TaskRepository;
import com.smartcity.urban_management.modules.user.repository.UserRepository;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskValidator {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public void validateStartTask(Task task, UUID userId) {

        // 1. Status
        if (task.getStatus() != TaskStatus.ASSIGNED) {
            throw new AppException(ErrorCode.TASK_INVALID_STATUS);
        }

        // 2. Check office membership (không lazy)
        UUID officeId = taskRepository.findOfficeIdByTaskId(task.getId());

        boolean isMember = userRepository.existsByIdAndOfficeId(
                userId,
                officeId
        );

        if (!isMember) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        // 3. Check claimed
        if (task.getAssignedUser() != null) {
            throw new AppException(ErrorCode.TASK_ALREADY_CLAIMED);
        }
    }

    public void validateCompleteTask(Task task, UUID userId, String note) {

        // 1. Status
        if (task.getStatus() != TaskStatus.IN_PROGRESS) {
            throw new AppException(ErrorCode.TASK_NOT_IN_PROGRESS);
        }

        // 2. Note
        if (note == null || note.isBlank()) {
            throw new AppException(ErrorCode.TASK_NOTE_REQUIRED);
        }

        // 3. Must be assigned
        if (task.getAssignedUser() == null) {
            throw new AppException(ErrorCode.TASK_NOT_ASSIGNED);
        }

        // 4. Must be correct user
        if (!task.getAssignedUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.TASK_NOT_CLAIMED_BY_USER);
        }
    }
}
