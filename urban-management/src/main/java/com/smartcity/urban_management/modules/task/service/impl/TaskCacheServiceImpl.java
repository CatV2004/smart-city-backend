package com.smartcity.urban_management.modules.task.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.ReportCacheService;
import com.smartcity.urban_management.infrastructure.redis.cache.TaskCacheService;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.service.ReportService;
import com.smartcity.urban_management.modules.task.dto.CompleteTaskRequest;
import com.smartcity.urban_management.modules.task.dto.TaskDetailResponse;
import com.smartcity.urban_management.modules.task.dto.TaskFilterRequest;
import com.smartcity.urban_management.modules.task.dto.TaskSummaryResponse;
import com.smartcity.urban_management.modules.task.service.TaskService;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class TaskCacheServiceImpl implements TaskService {

    private final TaskServiceImpl delegate;
    private final TaskCacheService taskCacheService;
    private final ReportService reportService;
    private final ReportCacheService reportCacheService;

    public UUID autoAssign(Report report) {
        UUID taskId = delegate.autoAssign(report);

        reportService.updateStatus(
                report,
                ReportStatus.ASSIGNED,
                "SYSTEM",
                "Auto-assigned to office"
        );

        taskCacheService.evictTaskDetail(taskId);
        taskCacheService.evictAllPages();

        return taskId;
    }

    @Override
    public TaskDetailResponse getDetail(UUID id) {

        // ===== CACHE HIT =====
        Optional<TaskDetailResponse> cached =
                taskCacheService.getTaskDetail(id);

        if (cached.isPresent()) {
            return cached.get();
        }

        // ===== DELEGATE =====
        TaskDetailResponse response = delegate.getDetail(id);

        // ===== CACHE SET =====
        taskCacheService.cacheTaskDetail(id, response);

        return response;
    }

    @Override
    public PageResponse<TaskSummaryResponse> getAll(
            TaskFilterRequest filter,
            PageRequestDto request,
            CustomUserDetails user
    ) {

        String filterKey = buildFilterKey(filter, user);

        Optional<PageResponse<TaskSummaryResponse>> cached =
                taskCacheService.getTaskPage(
                        request.getPage(),
                        request.getSize(),
                        request.getSort(),
                        filterKey
                );

        if (cached.isPresent()) {
            return cached.get();
        }

        PageResponse<TaskSummaryResponse> response =
                delegate.getAll(filter, request, user);

        taskCacheService.cacheTaskPage(
                request.getPage(),
                request.getSize(),
                request.getSort(),
                filterKey,
                response
        );

        return response;
    }

    @Override
    public Report startTask(UUID taskId, CustomUserDetails user) {

        Report report = delegate.startTask(taskId, user);

        taskCacheService.evictTaskDetail(taskId);
        taskCacheService.evictAllPages();

        reportCacheService.evictReport(report.getId());
        reportCacheService.evictAllReportPages();

        log.info("Start task {} → evicted task + report cache", taskId);

        return report;
    }

    @Override
    public Report completeTask(UUID taskId, CompleteTaskRequest request, CustomUserDetails user) {

        Report report = delegate.completeTask(taskId, request, user);

        taskCacheService.evictTaskDetail(taskId);
        taskCacheService.evictAllPages();

        reportCacheService.evictReport(report.getId());
        reportCacheService.evictAllReportPages();
        return report;
    }

    private String buildFilterKey(TaskFilterRequest filter, CustomUserDetails user) {
        String rolePart;
        if (user.hasRole("ADMIN")) {
            rolePart = "ADMIN";
        } else if (user.hasRole("STAFF")) {
            rolePart = "STAFF";
        } else {
            rolePart = "OTHER";
        }

        UUID officeId = delegate.resolveOfficeId(filter, user);
        String officePart = (officeId != null ? officeId.toString() : "all");

        String statusPart = (filter.getStatus() != null
                ? filter.getStatus().name()
                : "all");

        String keywordPart = delegate.normalizeKeyword(filter.getKeyword());
        if (keywordPart == null) {
            keywordPart = "none";
        }

        return String.join("_", rolePart, officePart, statusPart, keywordPart);
    }
}
