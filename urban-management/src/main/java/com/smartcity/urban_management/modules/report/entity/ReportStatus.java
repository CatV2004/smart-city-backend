package com.smartcity.urban_management.modules.report.entity;

public enum ReportStatus {

    PENDING("Pending review"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    IN_PROGRESS("In progress"),
    RESOLVED("Resolved"),
    CANCELLED("Cancelled");

    private final String description;

    ReportStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}