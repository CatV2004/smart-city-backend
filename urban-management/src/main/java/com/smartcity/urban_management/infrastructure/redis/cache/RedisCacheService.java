package com.smartcity.urban_management.infrastructure.redis.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    /* ================= GET ================= */

    public <T> Optional<T> get(String key, Class<T> clazz) {
        Object data = redisTemplate.opsForValue().get(key);

        if (data != null) {
            log.debug("✅ Redis HIT [{}]", key);
            return Optional.of(clazz.cast(data));
        }

        log.debug("❌ Redis MISS [{}]", key);
        return Optional.empty();
    }
    /* ================= SET ================= */

    public void set(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
        log.debug("🧠 Redis SET [{}], ttl={}m", key, ttl.toMinutes());
    }

    /* ================= DELETE ================= */

    public void delete(String key) {
        redisTemplate.delete(key);
        log.debug("🗑 Redis DELETE [{}]", key);
    }

    public void deleteAll(Collection<String> keys) {
        if (keys == null || keys.isEmpty()) return;

        redisTemplate.delete(keys);
        log.debug("🗑 Redis DELETE {} keys", keys.size());
    }

    /* ================= DELETE BY PATTERN (SCAN - PRODUCTION SAFE) ================= */

    public void deleteByPatternScan(String pattern) {

        ScanOptions options = ScanOptions.scanOptions()
                .match(pattern)
                .count(500)
                .build();

        var connection = Objects.requireNonNull(redisTemplate.getConnectionFactory())
                .getConnection();

        int totalDeleted = 0;

        try (Cursor<byte[]> cursor = connection.scan(options)) {

            List<byte[]> batch = new ArrayList<>(500);

            while (cursor.hasNext()) {
                batch.add(cursor.next());

                if (batch.size() >= 500) {
                    connection.del(batch.toArray(new byte[0][]));
                    totalDeleted += batch.size();
                    batch.clear();
                }
            }

            if (!batch.isEmpty()) {
                connection.del(batch.toArray(new byte[0][]));
                totalDeleted += batch.size();
            }

        } catch (Exception e) {
            log.error("❌ Redis SCAN delete error", e);
        }

        log.debug("🗑 Redis DELETE SCAN [{}] - {} keys", pattern, totalDeleted);
    }

    /* ================= (OPTIONAL - DEV ONLY) ================= */

    public void deleteByPattern(String pattern) {
        var keys = redisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
        log.warn("⚠ Using KEYS (not recommended in production) [{}]", pattern);
    }
}