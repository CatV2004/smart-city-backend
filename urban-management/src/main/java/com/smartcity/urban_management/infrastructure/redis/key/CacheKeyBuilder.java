package com.smartcity.urban_management.infrastructure.redis.key;

public final class CacheKeyBuilder {

    private CacheKeyBuilder() {}

    public static String detail(String prefix, Object identifier) {
        return "%s:detail:%s".formatted(prefix, identifier);
    }

    public static String list(String prefix) {
        return "%s:list".formatted(prefix);
    }

    public static String page(String prefix, int page, int size, String sort, String filter) {
        return "%s:page:%d:%d:%s:%s"
                .formatted(prefix, page, size, sort, safe(filter));
    }

    public static String pattern(String prefix, String type) {
        return "%s:%s:*".formatted(prefix, type);
    }

    private static String safe(String value) {
        return value == null ? "all" : value;
    }
}