package com.smartcity.urban_management.modules.report.dto;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ReportFilterRequest {

    private Set<ReportStatus> statuses;

    private ReportDisplayStatus displayStatus;

    private UUID categoryId;

    private String keyword;

}