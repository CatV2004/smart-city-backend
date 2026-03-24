package com.smartcity.urban_management.modules.report.mapper;

import com.smartcity.urban_management.modules.report.dto.ReportStatusHistoryResponse;
import com.smartcity.urban_management.modules.report.entity.ReportStatusHistory;

public class ReportStatusHistoryMapper {

    public static ReportStatusHistoryResponse toDto(ReportStatusHistory entity) {
        return ReportStatusHistoryResponse.builder()
                .fromStatus(entity.getFromStatus())
                .toStatus(entity.getToStatus())
                .changedBy(entity.getChangedBy())
                .note(entity.getNote())
                .changedAt(entity.getChangedAt())
                .build();
    }
}