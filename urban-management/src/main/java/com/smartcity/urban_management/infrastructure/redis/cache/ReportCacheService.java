package com.smartcity.urban_management.infrastructure.redis.cache;

import com.smartcity.urban_management.infrastructure.redis.key.ReportCacheKeys;
import com.smartcity.urban_management.modules.report.dto.summary.ReportCitizenSummaryResponse;
import com.smartcity.urban_management.modules.user.entity.RoleName;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisCacheService cacheService;

    private static final Duration DETAIL_TTL = Duration.ofMinutes(15);
    private static final Duration LIST_TTL = Duration.ofMinutes(3);

    /* ================= DETAIL ================= */

    public <T> Optional<T> getReport(UUID id, String role, Class<T> clazz) {
        String key = ReportCacheKeys.reportById(id, role);
        return cacheService.get(key, clazz);
    }

    public void cacheReport(UUID id, String role, Object report) {
        String key = ReportCacheKeys.reportById(id, role);
        cacheService.set(key, report, DETAIL_TTL);
    }

    /* ================= LIST ================= */

    public <T> Optional<PageResponse<T>> getReportPage(
            int page, int size, String sort, String filterKey, String role, Class<T> clazz) {

        String key = ReportCacheKeys.reportList(page, size, sort, filterKey, role);
        return cacheService.get(key, (Class) PageResponse.class);
    }

    public void cacheReportPage(
            int page, int size, String sort, String filterKey, String role, Object response) {

        String key = ReportCacheKeys.reportList(page, size, sort, filterKey, role);
        cacheService.set(key, response, LIST_TTL);
    }

    /* ================= USER LIST ================= */

    public Optional<PageResponse<ReportCitizenSummaryResponse>> getUserReportPage(
            UUID userId, int page, int size, String sort, String filterKey) {

        String key = ReportCacheKeys.userReportList(userId, page, size, sort, filterKey);
        return cacheService.get(key, (Class) PageResponse.class);
    }

    public void cacheUserReportPage(
            UUID userId,
            int page,
            int size,
            String sort,
            String filterKey,
            PageResponse<ReportCitizenSummaryResponse> response
    ) {
        String key = ReportCacheKeys.userReportList(userId, page, size, sort, filterKey);

        cacheService.set(key, response, LIST_TTL);

        log.debug(
                "🧠 Redis CACHE SET - user report page [{}], elements={}, ttl={}m",
                key,
                response.getContent().size(),
                LIST_TTL.toMinutes()
        );
    }

    /* ================= EVICT ================= */

    public void evictReport(UUID id) {

        List<String> keys = Arrays.stream(RoleName.values())
                .map(role -> ReportCacheKeys.reportById(id, role.name()))
                .toList();

        cacheService.deleteAll(keys);

        log.debug("🗑 Redis EVICT - report detail {}", keys);
    }

    public void evictAllReportPages() {
        cacheService.deleteByPatternScan(
                ReportCacheKeys.reportListPattern()
        );
    }

    public void evictUserReportPages(UUID userId) {
        cacheService.deleteByPatternScan(
                ReportCacheKeys.userReportListPattern(userId)
        );
    }
}