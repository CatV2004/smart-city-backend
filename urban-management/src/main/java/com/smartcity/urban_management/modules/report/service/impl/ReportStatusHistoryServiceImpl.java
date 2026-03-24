package com.smartcity.urban_management.modules.report.service.impl;

import com.smartcity.urban_management.modules.report.dto.ReportStatusHistoryResponse;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.entity.ReportStatusHistory;
import com.smartcity.urban_management.modules.report.mapper.ReportStatusHistoryMapper;
import com.smartcity.urban_management.modules.report.repository.ReportStatusHistoryRepository;
import com.smartcity.urban_management.modules.report.service.ReportStatusHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportStatusHistoryServiceImpl implements ReportStatusHistoryService {

    private final ReportStatusHistoryRepository repository;

    @Override
    public void createHistory(
            Report report,
            ReportStatus fromStatus,
            ReportStatus toStatus,
            String changedBy,
            String note
    ) {
        ReportStatusHistory history = ReportStatusHistory.builder()
                .report(report)
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .changedBy(changedBy)
                .note(note)
                .changedAt(LocalDateTime.now())
                .build();

        repository.save(history);
    }

    @Override
    public List<ReportStatusHistoryResponse> getHistoryByReportId(UUID reportId) {
        return repository.findByReportIdOrderByChangedAtAsc(reportId)
                .stream()
                .map(ReportStatusHistoryMapper::toDto)
                .toList();
    }
}