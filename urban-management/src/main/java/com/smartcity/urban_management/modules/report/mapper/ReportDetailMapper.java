package com.smartcity.urban_management.modules.report.mapper;

import com.smartcity.urban_management.modules.report.dto.ReportDisplayStatus;
import com.smartcity.urban_management.modules.report.dto.detail.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReportDetailMapper {

    // ===== COMMON =====
    private String resolveCategoryName(ReportDetailProjection p) {
        return p.getFinalCategoryName() != null
                ? p.getFinalCategoryName()
                : p.getUserCategoryName();
    }

    // ===== BASE BUILDER =====
    private void setBase(ReportDetailBaseResponse.ReportDetailBaseResponseBuilder<?, ?> builder,
                         ReportDetailProjection p) {

        builder
                .id(p.getId())
                .title(p.getTitle())
                .description(p.getDescription())
                .categoryName(resolveCategoryName(p))
                .latitude(p.getLatitude())
                .longitude(p.getLongitude())
                .address(p.getAddress())
                .createdByName(p.getCreatedByName())
                .createdByUserId(p.getCreatedByUserId())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt());
    }

    // ===== CITIZEN =====
    public ReportCitizenDetailResponse toCitizen(ReportDetailProjection p) {

        var builder = ReportCitizenDetailResponse.builder();

        setBase(builder, p);

        ReportDisplayStatus displayStatus = ReportDisplayStatus.fromReportStatus(p.getStatus());

        return builder
                .status(displayStatus)
                .build();
    }

    // ===== STAFF =====
    public ReportStaffDetailResponse toStaff(ReportDetailProjection p) {

        var builder = ReportStaffDetailResponse.builder();

        setBase(builder, p);

        return builder
                .categoryName(p.getUserCategoryName()) // override nếu cần
                .approvedByName(p.getApprovedByName())
                .status(p.getStatus())
                .build();
    }

    // ===== ADMIN =====
    public ReportAdminDetailResponse toAdmin(ReportDetailProjection p) {

        var builder = ReportAdminDetailResponse.builder();

        setBase(builder, p);

        return builder
                .userCategoryName(p.getUserCategoryName())
                .aiCategoryName(p.getAiCategoryName())
                .finalCategoryName(p.getFinalCategoryName())
                .aiConfidence(p.getAiConfidence())
                .approvedByName(p.getApprovedByName())
                .approvedById(p.getApprovedById())
                .status(p.getStatus())
                .build();
    }
}