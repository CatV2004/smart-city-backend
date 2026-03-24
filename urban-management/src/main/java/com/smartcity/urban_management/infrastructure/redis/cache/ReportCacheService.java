package com.smartcity.urban_management.infrastructure.redis.cache;

import com.smartcity.urban_management.infrastructure.redis.key.ReportCacheKeys;
import com.smartcity.urban_management.modules.report.dto.RecentReportData;
import com.smartcity.urban_management.modules.report.dto.summary.ReportCitizenSummaryResponse;
import com.smartcity.urban_management.modules.user.entity.RoleName;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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
    private static final Duration RECENT_TTL = Duration.ofMinutes(5);

    /* ================= DETAIL ================= */

    public <T> Optional<T> getReport(UUID id, String role, Class<T> clazz) {
        String key = ReportCacheKeys.reportById(id, role);
        return cacheService.get(key, clazz);
    }

    public void cacheReport(UUID id, String role, Object report) {
        String key = ReportCacheKeys.reportById(id, role);
        cacheService.set(key, report, DETAIL_TTL);
    }

    public void evictReport(UUID id) {
        List<String> keys = Arrays.stream(RoleName.values())
                .map(role -> ReportCacheKeys.reportById(id, role.name()))
                .toList();
        cacheService.deleteAll(keys);
        log.debug("🗑 Redis EVICT - report detail {}", keys);
    }

    /* ================= LIST / PAGE ================= */

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

    public void evictAllReportPages() {
        cacheService.deleteByPatternScan(ReportCacheKeys.reportListPattern());
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
        log.debug("🧠 Redis CACHE SET - user report page [{}], elements={}, ttl={}m",
                key, response.getContent().size(), LIST_TTL.toMinutes());
    }

    public void evictUserReportPages(UUID userId) {
        cacheService.deleteByPatternScan(ReportCacheKeys.userReportListPattern(userId));
    }

    /* ================= RECENT REPORTS (NEW) ================= */

    public Optional<RecentReportData> getRecentReports(UUID citizenId, int limit) {
        String key = ReportCacheKeys.recentReports(citizenId, limit);
        return cacheService.get(key, RecentReportData.class);
    }

    public void cacheRecentReports(UUID citizenId, int limit, RecentReportData reports) {
        String key = ReportCacheKeys.recentReports(citizenId, limit);
        cacheService.set(key, reports, RECENT_TTL);
        log.debug("🧠 Redis CACHE SET - recent reports [{}], elements={}, ttl={}m",
                key, reports.getRecentReportData().size(), RECENT_TTL.toMinutes());
    }

    public void evictRecentReports(UUID citizenId) {
        String pattern = ReportCacheKeys.recentReportsPattern(citizenId);
        cacheService.deleteByPatternScan(pattern);
        log.debug("🗑 Redis EVICT - recent reports [{}]", pattern);
    }
}