package com.smartcity.urban_management.infrastructure.redis.cache;

import com.smartcity.urban_management.infrastructure.redis.key.DepartmentCacheKeys;
import com.smartcity.urban_management.modules.department.dto.DepartmentDetailResponse;
import com.smartcity.urban_management.modules.department.dto.DepartmentSummaryResponse;
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
public class DepartmentCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration DETAIL_TTL = Duration.ofMinutes(30);
    private static final Duration PAGE_TTL = Duration.ofMinutes(10);
    private static final Duration LIST_TTL = Duration.ofMinutes(30);

    /* =========================================================
       DEPARTMENT DETAIL
       ========================================================= */

    public Optional<DepartmentDetailResponse> getDepartmentById(UUID id) {

        String key = DepartmentCacheKeys.departmentById(id);

        Object data = redisTemplate.opsForValue().get(key);

        if (data != null) {
            log.debug("✅ Redis HIT - department by id [{}]", key);
            return Optional.of((DepartmentDetailResponse) data);
        }

        log.debug("❌ Redis MISS - department by id [{}]", key);
        return Optional.empty();
    }

    public void cacheDepartmentById(UUID id, DepartmentDetailResponse department) {

        String key = DepartmentCacheKeys.departmentById(id);

        redisTemplate.opsForValue().set(key, department, DETAIL_TTL);

        log.debug(
                "🧠 Redis CACHE SET - department by id [{}], ttl={}m",
                key,
                DETAIL_TTL.toMinutes()
        );
    }

    public void evictDepartmentById(UUID id) {

        String key = DepartmentCacheKeys.departmentById(id);

        redisTemplate.delete(key);

        log.debug("🗑 Redis EVICT - department by id [{}]", key);
    }

    /* =========================================================
       DEPARTMENT BY CODE
       ========================================================= */

    public Optional<DepartmentDetailResponse> getDepartmentByCode(String code) {

        String key = DepartmentCacheKeys.departmentByCode(code);

        Object data = redisTemplate.opsForValue().get(key);

        if (data != null) {
            log.debug("✅ Redis HIT - department by code [{}]", key);
            return Optional.of((DepartmentDetailResponse) data);
        }

        log.debug("❌ Redis MISS - department by code [{}]", key);
        return Optional.empty();
    }

    public void cacheDepartmentByCode(String code, DepartmentDetailResponse department) {

        String key = DepartmentCacheKeys.departmentByCode(code);

        redisTemplate.opsForValue().set(key, department, DETAIL_TTL);

        log.debug(
                "🧠 Redis CACHE SET - department by code [{}], ttl={}m",
                key,
                DETAIL_TTL.toMinutes()
        );
    }

    public void evictDepartmentByCode(String code) {

        String key = DepartmentCacheKeys.departmentByCode(code);

        redisTemplate.delete(key);

        log.debug("🗑 Redis EVICT - department by code [{}]", key);
    }

    /* =========================================================
       DEPARTMENT PAGE (Pagination)
       ========================================================= */

    public Optional<PageResponse<DepartmentSummaryResponse>> getDepartmentPage(
            int page,
            int size,
            String sort,
            String filterKey
    ) {

        String key = DepartmentCacheKeys.departmentPage(page, size, sort, filterKey);

        Object data = redisTemplate.opsForValue().get(key);

        if (data != null) {
            log.debug("✅ Redis HIT - department page [{}]", key);
            return Optional.of((PageResponse<DepartmentSummaryResponse>) data);
        }

        log.debug("❌ Redis MISS - department page [{}]", key);
        return Optional.empty();
    }

    public void cacheDepartmentPage(
            int page,
            int size,
            String sort,
            String filterKey,
            PageResponse<DepartmentSummaryResponse> response
    ) {

        String key = DepartmentCacheKeys.departmentPage(page, size, sort, filterKey);

        redisTemplate.opsForValue().set(key, response, PAGE_TTL);

        log.debug(
                "🧠 Redis CACHE SET - department page [{}], ttl={}m",
                key,
                PAGE_TTL.toMinutes()
        );
    }

    public void evictAllPages() {

        String pattern = DepartmentCacheKeys.departmentPagePattern();

        redisTemplate.keys(pattern)
                .forEach(redisTemplate::delete);

        log.debug("🗑 Redis EVICT - all department pages [{}]", pattern);
    }

    /* =========================================================
       DEPARTMENT LIST
       ========================================================= */

    public Optional<Object> getDepartmentList() {

        String key = DepartmentCacheKeys.departmentList();

        Object data = redisTemplate.opsForValue().get(key);

        if (data != null) {
            log.debug("✅ Redis HIT - department list [{}]", key);
            return Optional.of(data);
        }

        log.debug("❌ Redis MISS - department list [{}]", key);
        return Optional.empty();
    }

    public void cacheDepartmentList(Object data) {

        String key = DepartmentCacheKeys.departmentList();

        redisTemplate.opsForValue().set(key, data, LIST_TTL);

        log.debug(
                "🧠 Redis CACHE SET - department list [{}], ttl={}m",
                key,
                LIST_TTL.toMinutes()
        );
    }

    public void evictDepartmentList() {

        String key = DepartmentCacheKeys.departmentList();

        redisTemplate.delete(key);

        log.debug("🗑 Redis EVICT - department list [{}]", key);
    }
}