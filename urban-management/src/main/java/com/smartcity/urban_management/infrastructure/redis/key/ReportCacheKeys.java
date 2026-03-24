package com.smartcity.urban_management.infrastructure.redis.key;

import java.util.UUID;

public final class ReportCacheKeys {

    private static final String PREFIX = CachePrefix.REPORT.value();

    public static String reportById(UUID id, String role) {
        return "%s:detail:%s:%s".formatted(PREFIX, role, id);
    }

    public static String reportList(int page, int size, String sort, String filterKey, String role) {
        return "%s:list:%s:%d:%d:%s:%s"
                .formatted(PREFIX, role, page, size, sort, filterKey);
    }

    public static String userReportList(UUID userId, int page, int size, String sort, String filterKey) {
        return "%s:list:user:%s:%d:%d:%s:%s"
                .formatted(PREFIX, userId, page, size, sort, filterKey);
    }

    public static String reportListPattern() {
        return "%s:list:*".formatted(PREFIX);
    }

    public static String userReportListPattern(UUID userId) {
        return "%s:list:user:%s:*".formatted(PREFIX, userId);
    }

    public static String recentReports(UUID citizenId, int limit) {
        return "%s:list:recent:%s:%d".formatted(PREFIX, citizenId, limit);
    }

    public static String recentReportsPattern(UUID citizenId) {
        return "%s:list:recent:%s:*".formatted(PREFIX, citizenId);
    }
}