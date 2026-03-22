package com.smartcity.urban_management.infrastructure.redis.key;

import java.util.UUID;

public final class DepartmentCacheKeys {

    private static final String PREFIX = CachePrefix.DEPARTMENT.value();

    public static String departmentById(UUID id) {
        return CacheKeyBuilder.detail(PREFIX, id);
    }

    public static String departmentByCode(String code) {
        return CacheKeyBuilder.detail(PREFIX, code);
    }

    public static String departmentList() {
        return CacheKeyBuilder.list(PREFIX);
    }

    public static String departmentPage(int page, int size, String sort, String filter) {
        return CacheKeyBuilder.page(PREFIX, page, size, sort, filter);
    }

    public static String departmentPagePattern() {
        return CacheKeyBuilder.pattern(PREFIX, "page");
    }
}