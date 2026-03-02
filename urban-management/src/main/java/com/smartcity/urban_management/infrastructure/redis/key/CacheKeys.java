package com.smartcity.urban_management.infrastructure.redis.key;

import java.util.UUID;

public final class CacheKeys {

    private CacheKeys() {}

    private static final String PREFIX = "urban:report:";

    /* ========= DETAIL ========= */

    public static String reportById(UUID id) {
        return PREFIX + "detail:" + id;
    }

    /* ========= LIST ========= */

    public static String reportList(int page, int size, String sort) {
        return PREFIX + "list:%d:%d:%s"
                .formatted(page, size, sort);
    }

    /* ========= PATTERN ========= */

    public static String reportListPattern() {
        return PREFIX + "list:*";
    }
}