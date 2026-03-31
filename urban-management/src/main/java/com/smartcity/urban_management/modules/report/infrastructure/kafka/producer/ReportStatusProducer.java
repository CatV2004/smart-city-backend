package com.smartcity.urban_management.modules.report.infrastructure.kafka.producer;

import com.smartcity.urban_management.modules.report.messaging.ReportStatusChangedMessage;
import com.smartcity.urban_management.shared.messaging.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportStatusKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishStatusChanged(ReportStatusChangedMessage message) {
        kafkaTemplate.send(KafkaTopics.REPORT_STATUS_CHANGED, message.getReportId().toString(), message);
    }
}