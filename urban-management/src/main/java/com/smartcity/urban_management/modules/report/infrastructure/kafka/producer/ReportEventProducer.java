package com.smartcity.urban_management.modules.report.infrastructure.kafka.producer;

import com.smartcity.urban_management.modules.report.messaging.ReportAttachmentsAddedMessage;
import com.smartcity.urban_management.modules.report.messaging.ReportCreatedMessage;
import com.smartcity.urban_management.shared.messaging.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishReportCreated(ReportCreatedMessage event) {
        kafkaTemplate.send(KafkaTopics.REPORT_CREATED, event);
    }

    public void publishAttachmentsAdded(ReportAttachmentsAddedMessage event) {
        kafkaTemplate.send(KafkaTopics.REPORT_ATTACHMENTS_ADDED, event);
    }
}