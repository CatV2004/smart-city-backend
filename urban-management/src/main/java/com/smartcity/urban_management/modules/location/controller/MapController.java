package com.smartcity.urban_management.modules.location.controller;

import com.smartcity.urban_management.modules.location.dto.MapDataResponse;
import com.smartcity.urban_management.modules.location.dto.MapFilterRequest;
import com.smartcity.urban_management.modules.location.service.MapService;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.task.entity.TaskStatus;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    @GetMapping("/map-data")
    public MapDataResponse getMapData(
            @RequestParam(required = false) List<ReportStatus> statuses,
            @RequestParam(required = false) List<UUID> categoryIds,
            @RequestParam(required = false) List<UUID> departmentIds,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "true") Boolean includeReports,
            @RequestParam(required = false, defaultValue = "true") Boolean includeOffices
    ) {
        MapFilterRequest filter = new MapFilterRequest();
        filter.setStatuses(statuses);
        filter.setCategoryIds(categoryIds);
        filter.setDepartmentIds(departmentIds);
        filter.setKeyword(keyword);
        filter.setIncludeReports(includeReports);
        filter.setIncludeOffices(includeOffices);

        return mapService.getMapData(filter);
    }

    @GetMapping("/staff/map-data")
    public MapDataResponse getStaffTaskMapData(
            @RequestParam(required = false) List<TaskStatus> taskStatuses,
            @RequestParam(required = false) String keyword,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        MapFilterRequest filter = new MapFilterRequest();
        filter.setTaskStatuses(taskStatuses);
        filter.setKeyword(keyword);

        return mapService.getTaskMapDataForStaff(filter, user.getId());
    }
}