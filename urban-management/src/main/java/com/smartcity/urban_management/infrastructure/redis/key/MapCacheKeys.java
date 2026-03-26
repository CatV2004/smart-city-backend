package com.smartcity.urban_management.infrastructure.redis.key;

public final class MapCacheKeys {

    private static final String PREFIX = CachePrefix.MAP.value();

    private MapCacheKeys() {}

    public static String mapData() {
        return CacheKeyBuilder.key(PREFIX, "data");
    }

}