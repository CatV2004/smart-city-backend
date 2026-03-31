package com.smartcity.urban_management.infrastructure.redis.cache;

import com.smartcity.urban_management.infrastructure.redis.key.MapCacheKeys;
import com.smartcity.urban_management.modules.location.dto.MapDataResponse;
import com.smartcity.urban_management.modules.location.dto.MapFilterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MapCacheService {

    private final RedisCacheService cacheService;

    private static final Duration MAP_TTL = Duration.ofMinutes(5);

    private String buildKey(MapFilterRequest filter) {
        return MapCacheKeys.mapData() + "::" + hashFilter(filter);
    }

    private String buildKey(UUID officeId, MapFilterRequest filter) {
        return MapCacheKeys.mapData()
                + "::office:" + officeId
                + "::" + hashFilter(filter);
    }

    private String hashFilter(MapFilterRequest filter) {
        try {
            return java.util.Base64.getEncoder().encodeToString(
                    new com.fasterxml.jackson.databind.ObjectMapper()
                            .writeValueAsBytes(filter)
            );
        } catch (Exception e) {
            throw new RuntimeException("Cannot hash filter", e);
        }
    }

    // ================= MAP DATA (ADMIN) =================
    public Optional<MapDataResponse> getMapData(MapFilterRequest filter) {
        return cacheService.get(buildKey(filter), MapDataResponse.class);
    }

    public void cacheMapData(MapFilterRequest filter, MapDataResponse data) {
        cacheService.set(buildKey(filter), data, MAP_TTL);
    }

    public void evictAllMapData() {
        cacheService.deleteByPatternScan(MapCacheKeys.mapData() + "::" + "*");
    }

    // ================= TASK MAP DATA (STAFF) =================
    public Optional<MapDataResponse> getTaskMapData(UUID officeId, MapFilterRequest filter) {
        return cacheService.get(buildKey(officeId, filter), MapDataResponse.class);
    }

    public void cacheTaskMapData(UUID officeId, MapFilterRequest filter, MapDataResponse data) {
        cacheService.set(buildKey(officeId, filter), data, MAP_TTL);
    }
}