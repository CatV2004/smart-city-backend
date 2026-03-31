package com.smartcity.urban_management.modules.report.mapper;

import com.smartcity.urban_management.modules.report.dto.ReportDisplayStatus;
import com.smartcity.urban_management.modules.report.dto.ReportResultDto;
import com.smartcity.urban_management.modules.report.dto.detail.*;
import com.smartcity.urban_management.modules.report.dto.summary.AttachmentSummaryResponse;
import com.smartcity.urban_management.modules.task.dto.TaskDetailProjection;
import com.smartcity.urban_management.modules.task.dto.TaskSummaryResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

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
                         ReportDetailProjection p, ReportResultDto result) {

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
                .updatedAt(p.getUpdatedAt())
                .result(result);
    }

    // ===== CITIZEN =====
    public ReportCitizenDetailResponse toCitizen(ReportDetailProjection p, ReportResultDto result) {

        var builder = ReportCitizenDetailResponse.builder();

        setBase(builder, p, result);

        ReportDisplayStatus displayStatus = ReportDisplayStatus.fromReportStatus(p.getStatus());

        return builder
                .status(displayStatus)
                .build();
    }

    // ===== ADMIN =====
    public ReportAdminDetailResponse toAdmin(ReportDetailProjection p, ReportResultDto result, TaskSummaryResponse task) {

        var builder = ReportAdminDetailResponse.builder();

        setBase(builder, p, result);

        return builder
                .userCategoryName(p.getUserCategoryName())
                .aiCategoryName(p.getAiCategoryName())
                .finalCategoryName(p.getFinalCategoryName())
                .aiConfidence(p.getAiConfidence())
                .approvedByName(p.getApprovedByName())
                .approvedById(p.getApprovedById())
                .status(p.getStatus())
                .task(task)
                .build();
    }

    public ReportStaffDetailResponse fromTaskProjection(
            TaskDetailProjection p,
            List<AttachmentSummaryResponse> attachments
    ) {

        return ReportStaffDetailResponse.builder()
                .id(p.getReportId())
                .title(p.getReportTitle())
                .description(p.getReportDescription())
                .categoryName(p.getCategoryName())
                .latitude(p.getLatitude())
                .longitude(p.getLongitude())
                .address(p.getAddress())
                .createdByName(p.getCreatedByName())
                .createdByUserId(p.getCreatedByUserId())
                .createdAt(p.getReportCreatedAt())
                .updatedAt(p.getReportUpdatedAt())
                .approvedByName(p.getApprovedByName())
                .status(p.getReportStatus())
                .attachments(attachments)
                .build();
    }
}