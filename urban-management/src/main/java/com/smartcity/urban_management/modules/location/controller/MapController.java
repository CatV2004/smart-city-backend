package com.smartcity.urban_management.modules.location.controller;

import com.smartcity.urban_management.modules.location.dto.MapDataResponse;
import com.smartcity.urban_management.modules.location.dto.MapFilterRequest;
import com.smartcity.urban_management.modules.location.service.MapService;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/map-data")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    @GetMapping
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
}