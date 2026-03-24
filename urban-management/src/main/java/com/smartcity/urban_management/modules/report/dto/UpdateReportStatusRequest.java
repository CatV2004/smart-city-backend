package com.smartcity.urban_management.modules.report.dto;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.Data;

@Data
public class UpdateReportStatusRequest {
    private ReportStatus status;

    private String note;
}
