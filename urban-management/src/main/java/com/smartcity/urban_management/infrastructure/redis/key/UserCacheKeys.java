package com.smartcity.urban_management.infrastructure.redis.key;


import java.util.UUID;

public final class UserCacheKeys {

    private static final String PREFIX = CachePrefix.USER.value();

    public static String userById(String slug) {
        return CacheKeyBuilder.detail(PREFIX, slug);
    }

    public static String userPage(int page, int size, String sort, String filter) {
        return CacheKeyBuilder.page(PREFIX, page, size, sort, filter);
    }

    public static String userPagePattern() {
        return PREFIX + ":page:*";
    }

    public static String departmentUsers(UUID departmentId, int page, int size, String sort) {
        return PREFIX + ":dept:%s:%d:%d:%s"
                .formatted(departmentId, page, size, sort);
    }

    public static String departmentUsersPattern(UUID departmentId) {
        return PREFIX + ":dept:%s:*".formatted(departmentId);
    }
}