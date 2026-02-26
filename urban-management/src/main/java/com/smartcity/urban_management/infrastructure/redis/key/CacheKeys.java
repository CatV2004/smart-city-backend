package com.smartcity.urban_management.infrastructure.redis.key;

public final class CacheKeys {

    private CacheKeys(){}

    public static String reportById(Object id) {
        return "report:id:" + id;
    }
}