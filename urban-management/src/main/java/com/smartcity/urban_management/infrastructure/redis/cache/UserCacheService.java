package com.smartcity.urban_management.infrastructure.redis.cache;

import com.smartcity.urban_management.infrastructure.redis.key.UserCacheKeys;
import com.smartcity.urban_management.modules.user.dto.UserSummaryResponse;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCacheService {

    private final RedisCacheService cacheService;

    private static final Duration PAGE_TTL = Duration.ofMinutes(10);   // management
    private static final Duration DEPT_TTL = Duration.ofMinutes(3);    // UI detail (ngắn hơn)

    /* =========================================================
       USER PAGE (MANAGEMENT)
       ========================================================= */

    public Optional<PageResponse<UserSummaryResponse>> getUserPage(
            int page,
            int size,
            String sort,
            String filterKey
    ) {

        String key = UserCacheKeys.userPage(page, size, sort, filterKey);

        return cacheService.get(key, (Class) PageResponse.class);
    }

    public void cacheUserPage(
            int page,
            int size,
            String sort,
            String filterKey,
            PageResponse<UserSummaryResponse> data
    ) {

        String key = UserCacheKeys.userPage(page, size, sort, filterKey);

        cacheService.set(key, data, PAGE_TTL);
    }

    public void evictAllUserPages() {
        cacheService.deleteByPattern(UserCacheKeys.userPagePattern());
    }

    /* =========================================================
       USERS BY DEPARTMENT (OPTIONAL CACHE)
       ========================================================= */

    public Optional<PageResponse<UserSummaryResponse>> getDepartmentUsers(
            UUID departmentId,
            int page,
            int size,
            String sort
    ) {

        String key = UserCacheKeys.departmentUsers(departmentId, page, size, sort);

        return cacheService.get(key, (Class) PageResponse.class);
    }

    public void cacheDepartmentUsers(
            UUID departmentId,
            int page,
            int size,
            String sort,
            PageResponse<UserSummaryResponse> data
    ) {

        String key = UserCacheKeys.departmentUsers(departmentId, page, size, sort);

        cacheService.set(key, data, DEPT_TTL);
    }

    public void evictDepartmentUsers(UUID departmentId) {
        cacheService.deleteByPattern(
                UserCacheKeys.departmentUsersPattern(departmentId)
        );
    }
}