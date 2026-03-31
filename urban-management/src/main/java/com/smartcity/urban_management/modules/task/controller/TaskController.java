package com.smartcity.urban_management.modules.task.controller;

import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.modules.task.dto.CompleteTaskRequest;
import com.smartcity.urban_management.modules.task.dto.TaskDetailResponse;
import com.smartcity.urban_management.modules.task.dto.TaskFilterRequest;
import com.smartcity.urban_management.modules.task.dto.TaskSummaryResponse;
import com.smartcity.urban_management.modules.task.entity.TaskStatus;
import com.smartcity.urban_management.modules.task.service.TaskService;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ReportRepository reportRepository;

    @Operation(summary = "Get tasks by department office")
    @GetMapping
    public PageResponse<TaskSummaryResponse> getAll(
            @RequestParam(required = false) UUID departmentOfficeId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) TaskStatus status,
            @ModelAttribute PageRequestDto request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {

        TaskFilterRequest filter = new TaskFilterRequest();
        filter.setDepartmentOfficeId(departmentOfficeId);
        filter.setKeyword(keyword);
        filter.setStatus(status);

        return taskService.getAll(filter, request, user);
    }

    @GetMapping("/{id}")
    public TaskDetailResponse getDetail(
            @PathVariable UUID id
    ) {
        return taskService.getDetail(id);
    }

    @PostMapping("/{reportId}")
    public UUID autoAssign(@PathVariable UUID reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        return taskService.autoAssign(report);
    }

    @PatchMapping("/{taskId}/start")
    public UUID startTask(
            @PathVariable UUID taskId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Report report = taskService.startTask(taskId, user);
        return report.getId();
    }

    @PostMapping(value = "/{taskId}/complete", consumes = "multipart/form-data")
    public UUID completeTask(
            @PathVariable UUID taskId,
            @ModelAttribute CompleteTaskRequest request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Report report = taskService.completeTask(taskId, request, user);
        return report.getId();
    }
}
