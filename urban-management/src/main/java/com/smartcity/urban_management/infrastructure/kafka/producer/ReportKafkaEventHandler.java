package com.smartcity.urban_management.infrastructure.kafka.producer;

import com.smartcity.urban_management.infrastructure.kafka.message.ReportCreatedMessage;
import com.smartcity.urban_management.shared.event.ReportCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportKafkaEventHandler {

    private final ReportEventProducer producer;

    /**
     * Lắng nghe event nội bộ của hệ thống
     * và chuyển thành Kafka message
     */
    @EventListener
    public void handleReportCreated(ReportCreatedEvent event) {

        log.info("Handling ReportCreatedEvent: {}", event.reportId());

        // map domain event → kafka message
        ReportCreatedMessage message = ReportCreatedMessage.builder()
                .reportId(event.reportId())
                .userId(event.userId())
                .title(event.title())
                .build();

        // publish ra Kafka
        producer.publishReportCreated(message);
    }
}