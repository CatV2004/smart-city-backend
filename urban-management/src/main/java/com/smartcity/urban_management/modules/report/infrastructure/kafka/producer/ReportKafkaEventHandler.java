package com.smartcity.urban_management.modules.report.infrastructure.kafka.producer;

import com.smartcity.urban_management.modules.report.messaging.ReportAttachmentsAddedMessage;
import com.smartcity.urban_management.modules.report.messaging.ReportCreatedMessage;
import com.smartcity.urban_management.shared.messaging.event.ReportAttachmentsAddedEvent;
import com.smartcity.urban_management.shared.messaging.event.ReportCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportKafkaEventHandler {

    private final ReportEventProducer producer;

    @EventListener
    public void handleReportCreated(ReportCreatedEvent event) {

        log.info("Handling ReportCreatedEvent: {}", event.reportId());

        ReportCreatedMessage message = ReportCreatedMessage.builder()
                .reportId(event.reportId())
                .userId(event.userId())
                .title(event.title())
                .description(event.description())
                .categoryId(event.categoryId())
                .latitude(event.latitude())
                .longitude(event.longitude())
                .address(event.address())
                .createdAt(event.createdAt())
                .build();

        producer.publishReportCreated(message);
    }

    @EventListener
    public void handleAttachmentsAdded(ReportAttachmentsAddedEvent event) {

        log.info("Handling ReportAttachmentsAddedEvent: {}", event.reportId());

        ReportAttachmentsAddedMessage message =
                ReportAttachmentsAddedMessage.builder()
                        .reportId(event.reportId())
                        .attachmentUrls(event.attachmentUrls())
                        .build();

        producer.publishAttachmentsAdded(message);
    }
}