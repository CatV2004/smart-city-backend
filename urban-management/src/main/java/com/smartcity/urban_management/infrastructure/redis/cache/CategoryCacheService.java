package com.smartcity.urban_management.infrastructure.redis.cache;

import com.smartcity.urban_management.infrastructure.redis.key.CategoryCacheKeys;
import com.smartcity.urban_management.modules.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration DETAIL_TTL = Duration.ofMinutes(30);
    private static final Duration LIST_TTL = Duration.ofHours(1);

    /* ===== DETAIL ===== */
    public Optional<CategoryResponse> getCategory(UUID id) {
        String key = CategoryCacheKeys.categoryById(id);
        Object data = redisTemplate.opsForValue().get(key);
        if (data != null) {
            log.debug("✅ Redis HIT - category detail [{}]", key);
            return Optional.of((CategoryResponse) data);
        }
        log.debug("❌ Redis MISS - category detail [{}]", key);
        return Optional.empty();
    }

    public void cacheCategory(UUID id, CategoryResponse category) {
        String key = CategoryCacheKeys.categoryById(id);
        redisTemplate.opsForValue().set(key, category, DETAIL_TTL);
        log.debug(
                "🧠 Redis CACHE SET - category detail [{}], ttl={}m",
                key,
                DETAIL_TTL.toMinutes()
        );
    }

    public void evictCategory(UUID id) {
        String key = CategoryCacheKeys.categoryById(id);
        redisTemplate.delete(key);
        log.debug("🗑 Redis EVICT - category detail [{}]", key);
    }

    /* ===== LIST ===== */
    public Optional<List<CategoryResponse>> getAll() {
        String key = CategoryCacheKeys.categoryList();
        Object data = redisTemplate.opsForValue().get(key);
        if (data != null) {
            log.debug("✅ Redis HIT - category list [{}]", key);
            return Optional.of((List<CategoryResponse>) data);
        }
        log.debug("❌ Redis MISS - category list [{}]", key);
        return Optional.empty();
    }

    public void cacheAll(List<CategoryResponse> categories) {
        String key = CategoryCacheKeys.categoryList();
        redisTemplate.opsForValue().set(key, categories, LIST_TTL);
        log.debug("🧠 Redis CACHE SET - category list [{}], ttl={}h", key, LIST_TTL.toHours());
    }

    public void evictAll() {
        String key = CategoryCacheKeys.categoryList();
        redisTemplate.delete(key);
        log.debug("🗑 Redis EVICT - category list [{}]", key);
    }
}