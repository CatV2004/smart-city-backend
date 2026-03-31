package com.smartcity.urban_management.modules.dashboard.admin.service;

import com.smartcity.urban_management.modules.dashboard.admin.dto.response.ResolvedReportResponse;
import com.smartcity.urban_management.modules.dashboard.admin.dto.response.ResolvedReportProjection;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResolvedReportService {

    private final ReportRepository reportRepository;

    @Transactional(readOnly = true)
    public Page<ResolvedReportResponse> getResolvedReports(Pageable pageable) {

        return reportRepository
                .findResolvedReports(ReportStatus.RESOLVED, pageable)
                .map(this::mapToResponse);
    }

    private ResolvedReportResponse mapToResponse(ResolvedReportProjection p) {
        return ResolvedReportResponse.builder()
                .reportId(p.getReportId())
                .title(p.getTitle())
                .status(p.getStatus())
                .createdAt(p.getCreatedAt())
                .taskId(p.getTaskId())
                .taskStatus(p.getTaskStatus())
                .assignedUserName(p.getAssignedUserName())
                .completedAt(p.getCompletedAt())
                .build();
    }
}
