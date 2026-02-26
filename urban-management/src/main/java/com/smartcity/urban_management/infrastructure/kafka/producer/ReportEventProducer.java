package com.smartcity.urban_management.infrastructure.kafka.producer;

import com.smartcity.urban_management.infrastructure.kafka.message.ReportCreatedMessage;
import com.smartcity.urban_management.shared.messaging.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = KafkaTopics.REPORT_CREATED;

    public void publishReportCreated(ReportCreatedMessage event) {
        kafkaTemplate.send(TOPIC, event.getReportId().toString(), event);
    }
}