package com.smartcity.urban_management.infrastructure.redis.cache;

import com.smartcity.urban_management.infrastructure.redis.key.CacheKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration TTL = Duration.ofMinutes(10);

    public Optional<Object> getReport(UUID id) {

        Object data =
                redisTemplate.opsForValue()
                        .get(CacheKeys.reportById(id));

        return Optional.ofNullable(data);
    }

    public void cacheReport(UUID id, Object report) {

        redisTemplate.opsForValue()
                .set(
                        CacheKeys.reportById(id),
                        report,
                        TTL
                );
    }

    public void evict(UUID id) {
        redisTemplate.delete(CacheKeys.reportById(id));
    }
}