package com.smartcity.urban_management.infrastructure.redis.key;

import java.util.UUID;

public final class ReportCacheKeys {

    private ReportCacheKeys() {
    }

    private static final String PREFIX = "urban:report:";

    /* ========= DETAIL ========= */

    public static String reportById(UUID id) {
        return PREFIX + "detail:" + id;
    }

    /* ========= LIST ========= */

    public static String reportList(
            int page,
            int size,
            String sort,
            String filterKey
    ) {
        return PREFIX + "list:%d:%d:%s:%s"
                .formatted(page, size, sort, filterKey);
    }

    /* ========= REPORTS OF USER LIST ========= */

    public static String userReportList(
            UUID userId,
            int page,
            int size,
            String sort,
            String filterKey
    ) {
        return PREFIX + "list:user:%s:%d:%d:%s:%s"
                .formatted(userId, page, size, sort, filterKey);
    }

    /* ========= PATTERN ========= */

    public static String reportListPattern() {
        return PREFIX + "list:*";
    }

    public static String userReportListPattern(UUID userId) {
        return PREFIX + "list:user:%s:*".formatted(userId);
    }
}