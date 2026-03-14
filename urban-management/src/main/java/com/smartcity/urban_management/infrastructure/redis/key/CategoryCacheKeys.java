package com.smartcity.urban_management.infrastructure.redis.key;

import java.util.UUID;

public final class CategoryCacheKeys {

    private static final String PREFIX = "category";

    public static String categoryById(UUID id) {
        return PREFIX + ":detail:" + id;
    }

    public static String categoryList() {
        return PREFIX + ":list";
    }

    public static String categoryPage(int page, int size, String sort, String filter) {
        return PREFIX + ":page:" + page + ":" + size + ":" + sort + ":" + filter;
    }

    public static String categoryPagePattern() {
        return PREFIX + ":page:*";
    }

}