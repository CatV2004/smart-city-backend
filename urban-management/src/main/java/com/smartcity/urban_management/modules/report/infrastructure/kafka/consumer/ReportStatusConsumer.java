package com.smartcity.urban_management.modules.report.infrastructure.kafka.consumer;

import com.smartcity.urban_management.modules.report.messaging.ReportStatusChangedMessage;
import com.smartcity.urban_management.modules.task.repository.TaskRepository;
import com.smartcity.urban_management.modules.task.service.TaskService;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.shared.messaging.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportStatusConsumer {

    private final ReportRepository reportRepository;
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @KafkaListener(
            topics = KafkaTopics.REPORT_STATUS_CHANGED,
            groupId = "report-status-assignment-group"
    )
    public void listenStatusChanged(
            ReportStatusChangedMessage message,
            Acknowledgment ack
    ) {

        log.info("[KafkaConsumer] Received ReportStatusChangedMessage | reportId={} | newStatus={}",
                message.getReportId(), message.getNewStatus());

        try {

            if (message.getNewStatus() != ReportStatus.VERIFIED
                    && message.getNewStatus() != ReportStatus.VERIFIED_AUTO) {

                log.debug("[KafkaConsumer] Status not eligible for auto-assign, skipping");

                ack.acknowledge();
                return;
            }

            boolean existed = taskRepository.existsByReportId(message.getReportId());

            if (existed) {
                log.warn("[KafkaConsumer] Task already exists for reportId={}, skipping auto-assign",
                        message.getReportId());

                ack.acknowledge();
                return;
            }

            Report report = reportRepository.findById(message.getReportId())
                    .orElseThrow(() -> new IllegalStateException(
                            "Report not found with id " + message.getReportId()
                    ));

            taskService.autoAssign(report);

            ack.acknowledge();

        } catch (Exception e) {
            log.error("❌ Failed to process auto assign", e);
            throw e; // Kafka retry
        }
    }
}