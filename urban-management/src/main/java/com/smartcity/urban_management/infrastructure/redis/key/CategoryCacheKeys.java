package com.smartcity.urban_management.infrastructure.redis.key;

public final class CategoryCacheKeys {

    private static final String PREFIX = CachePrefix.CATEGORY.value();

    public static String categoryBySlug(String slug) {
        return CacheKeyBuilder.detail(PREFIX, slug);
    }

    public static String categoryPage(int page, int size, String sort, String filter) {
        return CacheKeyBuilder.page(PREFIX, page, size, sort, filter);
    }

    public static String categoryPagePattern() {
        return CacheKeyBuilder.pattern(PREFIX, "page");
    }

    public static String categoryActiveList() {
        return CacheKeyBuilder.key(PREFIX, "active:list");
    }
}