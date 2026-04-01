package com.smartcity.urban_management.modules.notification.infrastructure.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcity.urban_management.modules.notification.service.NotificationService;
import com.smartcity.urban_management.modules.report.messaging.Prediction;
import com.smartcity.urban_management.modules.report.messaging.ReportAIAnalyzedMessage;
import com.smartcity.urban_management.modules.report.messaging.ReportCreatedMessage;
import com.smartcity.urban_management.modules.task.messaging.TaskAssignedMessage;
import com.smartcity.urban_management.modules.task.messaging.TaskCompletedMessage;
import com.smartcity.urban_management.modules.task.messaging.TaskStartedMessage;
import com.smartcity.urban_management.shared.messaging.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportAdminNotificationConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = KafkaTopics.REPORT_CREATED,
            groupId = "notification-admin-group"
    )
    public void handleNewReport(ReportCreatedMessage message, Acknowledgment ack) {
        log.info("[Admin Notification] New report received | reportId={}", message.getReportId());

        try {
            notificationService.notifyAdminsNewReport(message.getReportId(), message.getTitle());
            ack.acknowledge();
        } catch (Exception e) {
            log.error("❌ Failed to notify admins for new report", e);
            throw e;
        }
    }

    @KafkaListener(
            topics = KafkaTopics.REPORT_AI_ANALYZED,
            groupId = "notification-admin-group"
    )
    public void handleAIResult(ConsumerRecord<String, Object> record, Acknowledgment ack) {
        try {
            Object value = record.value();
            if (!(value instanceof Map)) {
                log.warn("[Admin Notification] Unexpected message type: {}", value.getClass());
                ack.acknowledge();
                return;
            }

            Map<String, Object> map = (Map<String, Object>) value;
            String type = (String) map.get("type");
            if (!"ReportAIAnalyzedMessage".equals(type)) {
                log.warn("[Admin Notification] Unknown type={} | value={}", type, map);
                ack.acknowledge();
                return;
            }

            ReportAIAnalyzedMessage message = objectMapper.convertValue(map, ReportAIAnalyzedMessage.class);

            if (message.getPredictions() != null && !message.getPredictions().isEmpty()) {
                Prediction prediction = message.getPredictions().get(0);

                log.info("[Admin Notification] AI analyzed report | reportId={} | label={} | confidence={}",
                        message.getReportId(),
                        prediction.getLabel(),
                        prediction.getConfidence());

                notificationService.notifyAdminsAIPredicted(
                        UUID.fromString(message.getReportId()),
                        prediction.getLabel(),
                        prediction.getConfidence()
                );
            } else {
                log.info("[Admin Notification] AI could not predict for report | reportId={}", message.getReportId());
            }

            ack.acknowledge();
        } catch (Exception e) {
            log.error("❌ Failed to notify admins for AI result", e);
            ack.acknowledge();
        }
    }

    @KafkaListener(
            topics = KafkaTopics.TASK_ASSIGNED,
            groupId = "notification-admin-group"
    )
    public void handleTaskAssigned(TaskAssignedMessage message, Acknowledgment ack) {

        log.info("[Admin Notification] Task assigned | reportId={} | office={}",
                message.getReportId(),
                message.getOfficeName()
        );

        try {
            notificationService.notifyAdminsTaskAssigned(
                    message.getReportId(),
                    message.getOfficeId(),
                    message.getOfficeName()
            );

            ack.acknowledge();
        } catch (Exception e) {
            log.error("❌ Failed to notify admins for task assigned", e);
            throw e;
        }
    }

    @KafkaListener(
            topics = KafkaTopics.TASK_STARTED,
            groupId = "notification-admin-group"
    )
    public void handleTaskStarted(TaskStartedMessage message, Acknowledgment ack) {

        try {
            notificationService.notifyAdminsTaskStarted(
                    message.getReportId(),
                    message.getUserId(),
                    message.getUserName()
            );

            ack.acknowledge();
        } catch (Exception e) {
            log.error("❌ Failed to notify admins for task started", e);
            throw e;
        }
    }

    @KafkaListener(
            topics = KafkaTopics.TASK_COMPLETED,
            groupId = "notification-admin-group"
    )
    public void handleTaskCompleted(TaskCompletedMessage message, Acknowledgment ack) {

        try {
            notificationService.notifyAdminsTaskCompleted(
                    message.getReportId(),
                    message.getUserId(),
                    message.getUserName(),
                    message.getNote()
            );

            ack.acknowledge();
        } catch (Exception e) {
            log.error("❌ Failed to notify admins", e);
            throw e;
        }
    }
}