package com.smartcity.urban_management.modules.report.service;

import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.entity.ReportStatusHistory;

import java.util.List;
import java.util.UUID;

public interface ReportStatusHistoryService {

    void createHistory(
            Report report,
            ReportStatus fromStatus,
            ReportStatus toStatus,
            String changedBy,
            String note
    );

    List<?> getHistoryByReportId(UUID reportId);
}