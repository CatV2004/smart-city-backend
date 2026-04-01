package com.smartcity.urban_management.modules.task.service;

import com.smartcity.urban_management.modules.location.dto.MapDataResponse;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.task.dto.CompleteTaskRequest;
import com.smartcity.urban_management.modules.task.dto.TaskDetailResponse;
import com.smartcity.urban_management.modules.task.dto.TaskFilterRequest;
import com.smartcity.urban_management.modules.task.dto.TaskSummaryResponse;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    UUID autoAssign(Report report);

    PageResponse<TaskSummaryResponse> getAll(TaskFilterRequest filter, PageRequestDto request, CustomUserDetails user);

    TaskDetailResponse getDetail(UUID id);

    Report startTask(UUID taskId, CustomUserDetails user);

    Report completeTask(UUID taskId, CompleteTaskRequest request, CustomUserDetails user);


}
