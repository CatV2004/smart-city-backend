package com.smartcity.urban_management.modules.task.infrastructure.kafka.producer;

import com.smartcity.urban_management.modules.task.entity.Task;
import com.smartcity.urban_management.modules.task.messaging.TaskAssignedMessage;
import com.smartcity.urban_management.modules.task.messaging.TaskCompletedMessage;
import com.smartcity.urban_management.modules.task.messaging.TaskStartedMessage;
import com.smartcity.urban_management.modules.task.repository.TaskRepository;
import com.smartcity.urban_management.shared.messaging.event.TaskAssignedEvent;
import com.smartcity.urban_management.shared.messaging.event.TaskCompletedEvent;
import com.smartcity.urban_management.shared.messaging.event.TaskStartedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskKafkaEventHandler {

    private final TaskEventProducer producer;
    private final TaskRepository taskRepository;

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTaskAssigned(TaskAssignedEvent event) {

        log.info("Handling TaskAssignedEvent: {}", event.getTaskId());

        Task task = taskRepository.findById(event.getTaskId())
                .orElseThrow();

        TaskAssignedMessage message = TaskAssignedMessage.builder()
                .taskId(task.getId())
                .reportId(task.getReport().getId())
                .officeId(task.getDepartmentOffice().getId())
                .officeName(task.getDepartmentOffice().getName())
                .assignedUserId(
                        task.getAssignedUser() != null
                                ? task.getAssignedUser().getId()
                                : null
                )
                .reportTitle(task.getReport().getTitle())
                .build();

        producer.publishTaskAssigned(message);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTaskStarted(TaskStartedEvent event) {

        log.info("Handling TaskStartedEvent: {}", event.getTaskId());

        TaskStartedMessage message = TaskStartedMessage.builder()
                .taskId(event.getTaskId())
                .reportId(event.getReportId())
                .userId(event.getUserId())
                .userName(event.getUserName())
                .build();

        producer.publishTaskStarted(message);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTaskCompleted(TaskCompletedEvent event) {

        log.info("Handling TaskCompletedEvent: {}", event.getTaskId());

        TaskCompletedMessage message = TaskCompletedMessage.builder()
                .taskId(event.getTaskId())
                .reportId(event.getReportId())
                .userId(event.getUserId())
                .userName(event.getUserName())
                .note(event.getNote())
                .build();

        producer.publishTaskCompleted(message);
    }
}
