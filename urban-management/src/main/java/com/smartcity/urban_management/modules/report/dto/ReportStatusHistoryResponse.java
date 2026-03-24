package com.smartcity.urban_management.modules.report.dto;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReportStatusHistoryResponse {

    private ReportStatus fromStatus;
    private ReportStatus toStatus;
    private String changedBy;
    private String note;
    private LocalDateTime changedAt;

}