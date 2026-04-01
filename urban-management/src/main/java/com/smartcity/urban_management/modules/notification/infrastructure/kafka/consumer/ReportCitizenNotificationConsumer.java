package com.smartcity.urban_management.modules.notification.infrastructure.kafka.consumer;

import com.smartcity.urban_management.modules.notification.service.NotificationService;
import com.smartcity.urban_management.modules.report.dto.ReportDisplayStatus;
import com.smartcity.urban_management.modules.report.messaging.ReportStatusChangedMessage;
import com.smartcity.urban_management.shared.messaging.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportCitizenNotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = KafkaTopics.REPORT_STATUS_CHANGED,
            groupId = "notification-citizen-group"
    )
    public void handle(ReportStatusChangedMessage message, Acknowledgment ack) {

        log.info("[Notification] Received REPORT_STATUS_CHANGED | reportId={} | newStatus={}",
                message.getReportId(), message.getNewStatus());

        try {
            var displayStatus = ReportDisplayStatus
                    .fromReportStatus(message.getNewStatus());

            switch (displayStatus) {

                case PROCESSING:
                    notificationService.createReportInProgressNotification(
                            message.getUserId(),
                            message.getReportId(),
                            message.getTitle()
                    );
                    break;

                case DONE:
                    notificationService.createReportResolvedNotification(
                            message.getUserId(),
                            message.getReportId(),
                            message.getTitle()
                    );
                    break;

                case REJECTED:
                    notificationService.createReportRejectedNotification(
                            message.getUserId(),
                            message.getReportId(),
                            message.getTitle()
                    );
                    break;

                default:
                    log.debug("No notification needed for status={}", displayStatus);
                    break;
            }
            ack.acknowledge();

        } catch (Exception e) {
            log.error("❌ Failed to process notification", e);
            throw e;
        }
    }
}