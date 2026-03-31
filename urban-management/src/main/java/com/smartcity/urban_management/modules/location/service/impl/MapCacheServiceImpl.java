package com.smartcity.urban_management.modules.location.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.MapCacheService;
import com.smartcity.urban_management.modules.location.dto.MapDataResponse;
import com.smartcity.urban_management.modules.location.dto.MapFilterRequest;
import com.smartcity.urban_management.modules.location.service.MapService;

import com.smartcity.urban_management.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class MapCacheServiceImpl implements MapService {

    private final MapCacheService cacheService;
    private final MapService delegate;
    private final UserRepository userRepository;

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

    @Override
    public MapDataResponse getTaskMapDataForStaff(MapFilterRequest filter, UUID userId) {
        UUID officeId = userRepository.findDepartmentOfficeIdByUserId(userId);

        var cached = cacheService.getTaskMapData(officeId, filter);

        if (cached.isPresent()) {
            log.info("🔥 Map cache HIT");
            return cached.get();
        }

        log.info("❌ Map cache MISS → load DB");

        MapDataResponse data = delegate.getTaskMapDataForStaff(filter, userId);

        cacheService.cacheTaskMapData(officeId, filter, data);

        return data;
    }
}