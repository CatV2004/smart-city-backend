package com.smartcity.urban_management.infrastructure.redis.cache;

import com.smartcity.urban_management.infrastructure.redis.key.CategoryCacheKeys;
import com.smartcity.urban_management.modules.category.dto.CategoryResponse;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration DETAIL_TTL = Duration.ofMinutes(30);
    private static final Duration PAGE_TTL = Duration.ofMinutes(10);
    private static final Duration LIST_TTL = Duration.ofMinutes(30);

    /* =========================================================
       CATEGORY DETAIL
       ========================================================= */

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
                "🧠 Redis CACHE SET - category detail [{}], ttl={}h",
                key,
                DETAIL_TTL.toHours()
        );
    }

    public void evictCategory(UUID id) {

        String key = CategoryCacheKeys.categoryById(id);

        redisTemplate.delete(key);

        log.debug("🗑 Redis EVICT - category detail [{}]", key);
    }

    /* =========================================================
       CATEGORY PAGE (Pagination)
       ========================================================= */

    public Optional<PageResponse<CategoryResponse>> getCategoryPage(
            int page,
            int size,
            String sort,
            String filterKey
    ) {

        String key = CategoryCacheKeys.categoryPage(page, size, sort, filterKey);

        Object data = redisTemplate.opsForValue().get(key);

        if (data != null) {
            log.debug("✅ Redis HIT - category page [{}]", key);
            return Optional.of((PageResponse<CategoryResponse>) data);
        }

        log.debug("❌ Redis MISS - category page [{}]", key);
        return Optional.empty();
    }

    public void cacheCategoryPage(
            int page,
            int size,
            String sort,
            String filterKey,
            com.smartcity.urban_management.shared.pagination.PageResponse<CategoryResponse> response
    ) {

        String key = CategoryCacheKeys.categoryPage(page, size, sort, filterKey);

        redisTemplate.opsForValue().set(key, response, PAGE_TTL);

        log.debug(
                "🧠 Redis CACHE SET - category page [{}], ttl={}h",
                key,
                PAGE_TTL.toHours()
        );
    }

    public void evictAllPages() {

        String pattern = CategoryCacheKeys.categoryPagePattern();

        redisTemplate.keys(pattern)
                .forEach(redisTemplate::delete);

        log.debug("🗑 Redis EVICT - all category pages [{}]", pattern);
    }

    /* =========================================================
       CATEGORY LIST (Dropdown API)
       ========================================================= */

    public Optional<Object> getAllCategories() {

        String key = CategoryCacheKeys.categoryList();

        Object data = redisTemplate.opsForValue().get(key);

        if (data != null) {
            log.debug("✅ Redis HIT - category list [{}]", key);
            return Optional.of(data);
        }

        log.debug("❌ Redis MISS - category list [{}]", key);
        return Optional.empty();
    }

    public void cacheAllCategories(Object categories) {

        String key = CategoryCacheKeys.categoryList();

        redisTemplate.opsForValue().set(key, categories, LIST_TTL);

        log.debug(
                "🧠 Redis CACHE SET - category list [{}], ttl={}h",
                key,
                LIST_TTL.toHours()
        );
    }

    public void evictAllCategories() {

        String key = CategoryCacheKeys.categoryList();

        redisTemplate.delete(key);

        log.debug("🗑 Redis EVICT - category list [{}]", key);
    }

}