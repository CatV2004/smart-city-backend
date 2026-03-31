package com.smartcity.urban_management.modules.dashboard.admin.service;

import com.smartcity.urban_management.modules.dashboard.admin.dto.response.PriorityReportResponse;
import com.smartcity.urban_management.modules.dashboard.admin.mapper.ReportMapper;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PriorityReportService {

    private final ReportRepository reportRepository;

    @Transactional(readOnly = true)
    public Page<PriorityReportResponse> getPriorityReports(Pageable pageable) {

        List<ReportStatus> statuses = List.of(
                ReportStatus.NEEDS_REVIEW,
                ReportStatus.LOW_CONFIDENCE
        );

        return reportRepository
                .findByStatusInAndDeletedAtIsNull(statuses, pageable)
                .map(ReportMapper::toPriorityResponse);
    }
}
