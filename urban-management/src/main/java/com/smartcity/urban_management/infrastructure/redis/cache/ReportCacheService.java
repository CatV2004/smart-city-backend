package com.smartcity.urban_management.infrastructure.redis.cache;

import com.smartcity.urban_management.infrastructure.redis.key.ReportCacheKeys;
import com.smartcity.urban_management.modules.report.dto.ReportDetailResponse;
import com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration DETAIL_TTL = Duration.ofMinutes(15);
    private static final Duration LIST_TTL = Duration.ofMinutes(3);

    /* ================= DETAIL ================= */

    public Optional<ReportDetailResponse> getReport(UUID id) {

        String key = ReportCacheKeys.reportById(id);

        Object data = redisTemplate.opsForValue().get(key);

        if (data != null) {
            log.debug("✅ Redis HIT - report detail [{}]", key);
            return Optional.of((ReportDetailResponse) data);
        }

        log.debug("❌ Redis MISS - report detail [{}]", key);
        return Optional.empty();
    }

    public void cacheReport(UUID id, ReportDetailResponse report) {

        String key = ReportCacheKeys.reportById(id);

        redisTemplate.opsForValue()
                .set(key, report, DETAIL_TTL);

        log.debug(
                "🧠 Redis CACHE SET - report detail [{}], ttl={}m",
                key,
                DETAIL_TTL.toMinutes()
        );
    }

    /* ================= LIST ================= */

    public Optional<PageResponse<ReportSummaryResponse>> getReportPage(
            int page,
            int size,
            String sort,
            String filterKey
    ) {

        String key = ReportCacheKeys.reportList(page, size, sort, filterKey);

        Object data = redisTemplate.opsForValue().get(key);

        if (data != null) {
            log.debug("✅ Redis HIT - report page [{}]", key);
            return Optional.of((PageResponse<ReportSummaryResponse>) data);
        }

        log.debug("❌ Redis MISS - report page [{}]", key);
        return Optional.empty();
    }

    public void cacheReportPage(
            int page,
            int size,
            String sort,
            String filterKey,
            PageResponse<ReportSummaryResponse> response
    ) {

        String key = ReportCacheKeys.reportList(page, size, sort, filterKey);

        redisTemplate.opsForValue()
                .set(key, response, LIST_TTL);

        log.debug(
                "🧠 Redis CACHE SET - report page [{}], elements={}, ttl={}m",
                key,
                response.getContent().size(),
                LIST_TTL.toMinutes()
        );
    }

    /* ================= USER LIST ================= */

    public Optional<PageResponse<ReportSummaryResponse>> getUserReportPage(
            UUID userId,
            int page,
            int size,
            String sort,
            String filterKey
    ) {

        String key = ReportCacheKeys.userReportList(userId, page, size, sort, filterKey);

        Object data = redisTemplate.opsForValue().get(key);

        if (data != null) {
            log.debug("✅ Redis HIT - user report page [{}]", key);
            return Optional.of((PageResponse<ReportSummaryResponse>) data);
        }

        log.debug("❌ Redis MISS - user report page [{}]", key);
        return Optional.empty();
    }

    public void cacheUserReportPage(
            UUID userId,
            int page,
            int size,
            String sort,
            String filterKey,
            PageResponse<ReportSummaryResponse> response
    ) {

        String key = ReportCacheKeys.userReportList(userId, page, size, sort, filterKey);

        redisTemplate.opsForValue()
                .set(key, response, LIST_TTL);

        log.debug(
                "🧠 Redis CACHE SET - user report page [{}], elements={}, ttl={}m",
                key,
                response.getContent().size(),
                LIST_TTL.toMinutes()
        );
    }

    /* ================= EVICT ================= */

    public void evictReport(UUID id) {

        String key = ReportCacheKeys.reportById(id);

        redisTemplate.delete(key);

        log.debug("🗑 Redis EVICT - report detail [{}]", key);
    }

    public void evictAllReportPages() {

        String pattern = ReportCacheKeys.reportListPattern();

        ScanOptions options = ScanOptions.scanOptions()
                .match(pattern)
                .count(100)
                .build();

        Cursor<byte[]> cursor =
                redisTemplate.getConnectionFactory()
                        .getConnection()
                        .scan(options);

        List<String> keys = new ArrayList<>();

        cursor.forEachRemaining(key ->
                keys.add(new String(key))
        );

        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
            log.debug("🗑 Redis EVICT ALL report pages [{} keys]", keys.size());
        }
    }

    public void evictUserReportPages(UUID userId) {

        String pattern = ReportCacheKeys.userReportListPattern(userId);

        ScanOptions options = ScanOptions.scanOptions()
                .match(pattern)
                .count(100)
                .build();

        Cursor<byte[]> cursor =
                redisTemplate.getConnectionFactory()
                        .getConnection()
                        .scan(options);

        List<String> keys = new ArrayList<>();

        cursor.forEachRemaining(key ->
                keys.add(new String(key))
        );

        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
            log.debug("🗑 Redis EVICT user report pages [{} keys]", keys.size());
        }
    }
}