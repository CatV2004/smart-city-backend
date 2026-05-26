package com.smartcity.urban_management.modules.location.infrastructure.kafka.consumer;

import com.smartcity.urban_management.modules.location.realtime.MapRealtimePublisher;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.messaging.ReportCreatedMessage;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.modules.task.messaging.TaskAssignedMessage;
import com.smartcity.urban_management.modules.task.messaging.TaskCompletedMessage;
import com.smartcity.urban_management.shared.messaging.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class MapRealtimeConsumer {

    private final ReportRepository reportRepository;
    private final MapRealtimePublisher mapRealtimePublisher;

    @KafkaListener(
            topics = KafkaTopics.REPORT_CREATED,
            groupId = "map-realtime-group"
    )
    public void handleReportCreated(
            ReportCreatedMessage message,
            Acknowledgment ack
    ) {

        try {

            Report report = getReport(message.getReportId());

            mapRealtimePublisher.created(report);

            ack.acknowledge();

        } catch (Exception e) {

            log.error(
                    "[MapRealtime] Failed to handle REPORT_CREATED | reportId={}",
                    message.getReportId(),
                    e
            );

            throw e;
        }
    }

    @KafkaListener(
            topics = KafkaTopics.TASK_ASSIGNED,
            groupId = "map-realtime-group"
    )
    public void handleTaskAssigned(
            TaskAssignedMessage message,
            Acknowledgment ack
    ) {

        try {

            Report report = getReport(message.getReportId());

            mapRealtimePublisher.updated(report);

            ack.acknowledge();

        } catch (Exception e) {

            log.error(
                    "[MapRealtime] Failed to handle TASK_ASSIGNED | reportId={}",
                    message.getReportId(),
                    e
            );

            throw e;
        }
    }

    @KafkaListener(
            topics = KafkaTopics.TASK_COMPLETED,
            groupId = "map-realtime-group"
    )
    public void handleTaskCompleted(
            TaskCompletedMessage message,
            Acknowledgment ack
    ) {

        try {

            Report report = getReport(message.getReportId());

            mapRealtimePublisher.updated(report);

            ack.acknowledge();

        } catch (Exception e) {

            log.error(
                    "[MapRealtime] Failed to handle TASK_COMPLETED | reportId={}",
                    message.getReportId(),
                    e
            );

            throw e;
        }
    }

    private Report getReport(UUID reportId) {

        return reportRepository
                .findByIdWithRelations(reportId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Report not found: " + reportId
                        )
                );
    }
}