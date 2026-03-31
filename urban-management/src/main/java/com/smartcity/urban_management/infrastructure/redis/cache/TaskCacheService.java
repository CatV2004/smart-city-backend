package com.smartcity.urban_management.infrastructure.redis.cache;

import com.smartcity.urban_management.infrastructure.redis.key.TaskCacheKeys;
import com.smartcity.urban_management.modules.task.dto.TaskDetailResponse;
import com.smartcity.urban_management.modules.task.dto.TaskSummaryResponse;
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
public class TaskCacheService {

    private final RedisCacheService cacheService;

    private static final Duration DETAIL_TTL = Duration.ofMinutes(10);
    private static final Duration PAGE_TTL = Duration.ofMinutes(5);

    /* =========================================================
       TASK DETAIL
       ========================================================= */

    public Optional<TaskDetailResponse> getTaskDetail(UUID id) {
        String key = TaskCacheKeys.taskDetail(id);
        return cacheService.get(key, TaskDetailResponse.class);
    }

    public void cacheTaskDetail(UUID id, TaskDetailResponse data) {
        String key = TaskCacheKeys.taskDetail(id);
        cacheService.set(key, data, DETAIL_TTL);
    }

    public void evictTaskDetail(UUID id) {
        cacheService.delete(TaskCacheKeys.taskDetail(id));
    }

    /* =========================================================
       TASK PAGE
       ========================================================= */

    public Optional<PageResponse<TaskSummaryResponse>> getTaskPage(
            int page, int size, String sort, String filterKey) {

        String key = TaskCacheKeys.taskPage(page, size, sort, filterKey);
        return cacheService.get(key, (Class) PageResponse.class);
    }

    public void cacheTaskPage(
            int page, int size, String sort, String filterKey,
            PageResponse<TaskSummaryResponse> data) {

        String key = TaskCacheKeys.taskPage(page, size, sort, filterKey);
        cacheService.set(key, data, PAGE_TTL);
    }

    public void evictAllPages() {
        cacheService.deleteByPattern(TaskCacheKeys.taskPagePattern());
    }
}