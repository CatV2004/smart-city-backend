package com.smartcity.urban_management.infrastructure.redis.key;

import java.util.UUID;

public final class TaskCacheKeys {

    private static final String PREFIX = CachePrefix.TASK.value();

    // ===== DETAIL =====
    public static String taskDetail(UUID id) {
        return CacheKeyBuilder.detail(PREFIX, id.toString());
    }

    // ===== PAGE =====
    public static String taskPage(int page, int size, String sort, String filterKey) {
        return CacheKeyBuilder.page(PREFIX, page, size, sort, filterKey);
    }

    public static String taskPagePattern() {
        return CacheKeyBuilder.pattern(PREFIX, "page");
    }
}