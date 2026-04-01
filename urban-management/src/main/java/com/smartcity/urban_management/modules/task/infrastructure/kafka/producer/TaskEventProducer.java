package com.smartcity.urban_management.modules.task.infrastructure.kafka.producer;

import com.smartcity.urban_management.modules.task.messaging.TaskAssignedMessage;
import com.smartcity.urban_management.modules.task.messaging.TaskCompletedMessage;
import com.smartcity.urban_management.modules.task.messaging.TaskStartedMessage;
import com.smartcity.urban_management.shared.messaging.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishTaskAssigned(TaskAssignedMessage message) {
        kafkaTemplate.send(KafkaTopics.TASK_ASSIGNED, message);
    }

    public void publishTaskStarted(TaskStartedMessage message) {
        kafkaTemplate.send(KafkaTopics.TASK_STARTED, message);
    }

    public void publishTaskCompleted(TaskCompletedMessage message) {
        kafkaTemplate.send(KafkaTopics.TASK_COMPLETED, message);
    }
}
