package com.smartcity.urban_management.modules.report.dto;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportFilterRequest {

    private ReportStatus status;

    private String category;

    private String keyword;

}