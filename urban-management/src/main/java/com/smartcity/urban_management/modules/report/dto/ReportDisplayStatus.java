package com.smartcity.urban_management.modules.report.dto;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;

import java.util.EnumSet;
import java.util.Set;

public enum ReportDisplayStatus {

    PENDING("Đã tiếp nhận"),
    PROCESSING("Đang xử lý"),
    DONE("Đã xử lý xong"),
    REJECTED("Bị từ chối");

    private final String label;

    ReportDisplayStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ReportDisplayStatus fromReportStatus(ReportStatus status) {
        switch (status) {
            case PENDING:
                return PENDING;

            case VERIFIED_AUTO:
            case NEEDS_REVIEW:
            case LOW_CONFIDENCE:
            case ASSIGNED:
            case IN_PROGRESS:
            case VERIFIED:
                return PROCESSING;

            case RESOLVED:
            case CLOSED:
                return DONE;

            case REJECTED:
                return REJECTED;

            default:
                throw new IllegalArgumentException("Unknown status: " + status);
        }
    }

    public Set<ReportStatus> getOriginalStatuses() {
        switch (this) {
            case PENDING: return EnumSet.of(ReportStatus.PENDING);
            case PROCESSING: return EnumSet.of(
                    ReportStatus.VERIFIED_AUTO,
                    ReportStatus.NEEDS_REVIEW,
                    ReportStatus.LOW_CONFIDENCE,
                    ReportStatus.ASSIGNED,
                    ReportStatus.IN_PROGRESS,
                    ReportStatus.VERIFIED
            );
            case DONE: return EnumSet.of(ReportStatus.RESOLVED, ReportStatus.CLOSED);
            case REJECTED: return EnumSet.of(ReportStatus.REJECTED);
            default: throw new IllegalArgumentException();
        }
    }
}