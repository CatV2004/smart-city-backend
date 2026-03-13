package com.smartcity.urban_management.infrastructure.redis.key;

import java.util.UUID;

public final class CategoryCacheKeys {

    private CategoryCacheKeys() {}

    private static final String PREFIX = "urban:category:";

    /* ===== DETAIL ===== */
    public static String categoryById(UUID id) {
        return PREFIX + "detail:" + id;
    }

    /* ===== LIST ===== */
    public static String categoryList() {
        return PREFIX + "list:all";
    }

    /* ===== PATTERN ===== */
    public static String categoryListPattern() {
        return PREFIX + "list:*";
    }
}