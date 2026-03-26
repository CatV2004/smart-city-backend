package com.smartcity.urban_management.infrastructure.redis.key;

import java.util.UUID;

public final class OfficeCacheKeys {

    private static final String PREFIX = CachePrefix.OFFICE.value();

    private OfficeCacheKeys() {}

    /* =========================================================
       OFFICE DETAIL
       ========================================================= */
    public static String officeDetail(Object id) {
        return CacheKeyBuilder.detail(PREFIX, id);
    }

    /* =========================================================
       OFFICE PAGE (theo department)
       ========================================================= */
    public static String officePage(UUID departmentId, int page, int size, String sort) {
        return CacheKeyBuilder.page(PREFIX, page, size, sort, departmentId.toString());
    }

    public static String officePagePattern() {
        return CacheKeyBuilder.pattern(PREFIX, "page");
    }
}