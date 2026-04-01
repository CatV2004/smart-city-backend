package com.smartcity.urban_management.modules.notification.infrastructure.kafka.consumer;

import com.smartcity.urban_management.modules.report.messaging.ReportCreatedMessage;
import com.smartcity.urban_management.modules.notification.service.NotificationService;
import com.smartcity.urban_management.shared.messaging.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportCreatedConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = KafkaTopics.REPORT_CREATED,
            groupId = "notification-group"
    )
    public void handleReportCreated(
            ConsumerRecord<String, ReportCreatedMessage> record,
            Acknowledgment ack
    ) {

        ReportCreatedMessage message = record.value();

        try {
            log.info(
                    "📩 Received REPORT_CREATED event: reportId={}, partition={}, offset={}",
                    message.getReportId(),
                    record.partition(),
                    record.offset()
            );

            notificationService.createReportCreatedNotification(
                    message.getUserId(),
                    message.getReportId(),
                    message.getTitle()
            );

            ack.acknowledge();

        } catch (Exception ex) {

            log.error("❌ Failed processing REPORT_CREATED", ex);

            throw ex;
        }
    }
}