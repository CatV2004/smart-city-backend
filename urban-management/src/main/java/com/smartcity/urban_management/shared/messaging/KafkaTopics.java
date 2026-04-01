package com.smartcity.urban_management.shared.messaging;

public final class KafkaTopics {

    public static final String REPORT_CREATED = "report.created";
    public static final String NOTIFICATION_SEND = "notification.send";
    public static final String REPORT_ATTACHMENTS_ADDED = "report.attachments.added";
    public static final String REPORT_AI_ANALYZED = "report-ai-analyzed";
    public static final String REPORT_STATUS_CHANGED = "report.status.changed";
    public static final String REPORT_IN_PROGRESS = "report.in-progress";
    public static final String TASK_ASSIGNED = "task.assigned";
    public static final String TASK_STARTED = "task.started";
    public static final String TASK_COMPLETED = "task.completed";

    private KafkaTopics() {}
}