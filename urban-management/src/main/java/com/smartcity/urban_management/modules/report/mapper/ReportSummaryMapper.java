package com.smartcity.urban_management.modules.report.mapper;

import com.smartcity.urban_management.modules.report.dto.ReportDisplayStatus;
import com.smartcity.urban_management.modules.report.dto.summary.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReportSummaryMapper {

    private String resolveCategoryName(ReportSummaryProjection p) {
        return p.getFinalCategoryName() != null
                ? p.getFinalCategoryName()
                : p.getUserCategoryName();
    }

    // ===== BASE =====
    private void setBase(ReportSummaryBaseResponse.ReportSummaryBaseResponseBuilder<?, ?> builder,
                         ReportSummaryProjection p) {

        builder
                .id(p.getId())
                .title(p.getTitle())
//                .status(p.getStatus())
                .categoryName(resolveCategoryName(p))
                .address(p.getAddress())
                .createdByName(p.getCreatedByName())
                .createdByUserId(p.getCreatedByUserId())
                .createdAt(p.getCreatedAt());
    }

    // ===== ADMIN =====
    public ReportAdminSummaryResponse toAdmin(ReportSummaryProjection p) {

        var builder = ReportAdminSummaryResponse.builder();

        setBase(builder, p);

        return builder
                .status(p.getStatus())
                .build();
    }

    // ===== STAFF =====
    public ReportStaffSummaryResponse toStaff(ReportSummaryProjection p) {

        var builder = ReportStaffSummaryResponse.builder();

        setBase(builder, p);

        return builder
                .priority(p.getPriority())
                .status(p.getStatus())
                .build();
    }

    // ===== CITIZEN =====
    public ReportCitizenSummaryResponse toCitizen(ReportSummaryProjection p) {

        var builder = ReportCitizenSummaryResponse.builder();

        setBase(builder, p);
        ReportDisplayStatus displayStatus = ReportDisplayStatus.fromReportStatus(p.getStatus());

        return builder
                .description(p.getDescription())
                .status(displayStatus)
                .build();
    }
}