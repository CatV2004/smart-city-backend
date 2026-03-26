package com.smartcity.urban_management.modules.location.dto;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MapFilterRequest {

    private Boolean includeReports = true;
    private Boolean includeOffices = true;

    private List<ReportStatus> statuses;
    private List<UUID> categoryIds;
    private List<UUID> departmentIds;

    private String keyword;
}