package com.smartcity.urban_management.modules.notification.infrastructure.kafka.consumer;

import com.smartcity.urban_management.modules.notification.service.NotificationService;
import com.smartcity.urban_management.modules.task.messaging.TaskAssignedMessage;
import com.smartcity.urban_management.modules.user.entity.User;
import com.smartcity.urban_management.modules.user.repository.UserRepository;
import com.smartcity.urban_management.shared.messaging.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskStaffNotificationConsumer {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @KafkaListener(
            topics = KafkaTopics.TASK_ASSIGNED,
            groupId = "notification-staff-group"
    )
    @Transactional
    public void handleTaskAssigned(TaskAssignedMessage message, Acknowledgment ack) {
        try {
            List<User> users = userRepository.findByOfficeId(message.getOfficeId());
            log.info("Found {} staff members in office {}", users.size(), message.getOfficeId());

            for (User user : users) {
                notificationService.notifyStaffNewTask(
                        user.getId(),
                        message.getTaskId(),
                        message.getReportTitle()
                );
            }

            ack.acknowledge();
        } catch (Exception e) {
            log.error("❌ Failed to notify staff", e);
            throw e;
        }
    }
}
