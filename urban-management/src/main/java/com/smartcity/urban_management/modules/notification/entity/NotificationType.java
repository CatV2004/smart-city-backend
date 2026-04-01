package com.smartcity.urban_management.modules.notification.entity;

public enum NotificationType {

    // Citizen
    REPORT_CREATED,
    REPORT_IN_PROGRESS,
    REPORT_RESOLVED,
    REPORT_REJECTED,

    // Admin / Staff
    NEW_REPORT_RECEIVED,          // có report mới
    AI_PREDICTED,                 // AI đã phân loại
    TASK_ASSIGNED,                // assign cho staff
    TASK_STARTED,                 // staff bắt đầu
    TASK_COMPLETED,               // staff hoàn thành

    SYSTEM
}