package com.smartcity.urban_management.modules.task.listener;

import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.event.ReportStatusChangedEvent;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.modules.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskEventListener {

    private final TaskService taskService;
    private final ReportRepository reportRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReportStatusChanged(ReportStatusChangedEvent event) {

        log.debug("[Assignment] Received event | reportId={} | newStatus={}",
                event.reportId(), event.newStatus());

        if (event.newStatus() != ReportStatus.VERIFIED_AUTO
                && event.newStatus() != ReportStatus.VERIFIED) {
            return;
        }

        Report report = reportRepository.findById(event.reportId())
                .orElseThrow();

        taskService.autoAssign(report);
    }
}