package com.smartcity.urban_management.modules.report.entity;

public enum ReportStatus {

    // ========== INITIAL ==========
    PENDING("Waiting for AI processing"),

    // ========== AI PROCESSING ==========
    VERIFIED_AUTO("Automatically verified by AI"),
    NEEDS_REVIEW("AI result differs from user input"),
    LOW_CONFIDENCE("AI confidence is low, needs manual review"),

    // ========== HUMAN VERIFICATION ==========
    VERIFIED("Verified by admin"),
    REJECTED("Rejected by admin"),

    // ========== TASK PROCESSING ==========
    ASSIGNED("Assigned to department"),
    IN_PROGRESS("Being processed"),
    RESOLVED("Issue has been resolved"),

    // ========== FINAL ==========
    CLOSED("Report closed");

    private final String description;

    ReportStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}