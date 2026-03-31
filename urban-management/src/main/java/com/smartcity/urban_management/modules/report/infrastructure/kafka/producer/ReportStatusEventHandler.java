package com.smartcity.urban_management.modules.report.infrastructure.kafka.producer;

import com.smartcity.urban_management.modules.report.messaging.ReportStatusChangedMessage;
import com.smartcity.urban_management.shared.messaging.event.ReportStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportStatusEventHandler {

    private final ReportStatusProducer producer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReportStatusChanged(ReportStatusChangedEvent event) {

        log.info("Handling ReportStatusChangedEvent: {}", event.getReportId());

        ReportStatusChangedMessage message = ReportStatusChangedMessage.builder()
                .reportId(event.getReportId())
                .oldStatus(event.getOldStatus())
                .newStatus(event.getNewStatus())
                .changedAt(LocalDateTime.now())
                .changedBy(event.getChangedBy())
                .build();

        producer.publishStatusChanged(message);
    }
}
