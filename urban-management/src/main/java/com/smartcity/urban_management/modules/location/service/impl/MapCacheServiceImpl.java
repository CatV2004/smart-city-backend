package com.smartcity.urban_management.modules.location.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.MapCacheService;
import com.smartcity.urban_management.modules.location.dto.MapDataResponse;
import com.smartcity.urban_management.modules.location.dto.MapFilterRequest;
import com.smartcity.urban_management.modules.location.service.MapService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class MapCacheServiceImpl implements MapService {

    private final MapCacheService cacheService;
    private final @Qualifier("mapServiceImpl") MapService delegate;

    @Override
    public MapDataResponse getMapData(MapFilterRequest filter) {

        var cached = cacheService.getMapData(filter);

        if (cached.isPresent()) {
            log.info("🔥 Map cache HIT");
            return cached.get();
        }

        log.info("❌ Map cache MISS → load DB");

        MapDataResponse data = delegate.getMapData(filter);

        cacheService.cacheMapData(filter, data);

        return data;
    }
}