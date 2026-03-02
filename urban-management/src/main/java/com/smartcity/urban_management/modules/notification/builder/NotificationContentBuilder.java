package com.smartcity.urban_management.modules.notification.builder;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotificationContentBuilder {

    public String buildReportCreated(String title) {
        return "Phản ánh \"" + title + "\" đã được gửi thành công.";
    }
}