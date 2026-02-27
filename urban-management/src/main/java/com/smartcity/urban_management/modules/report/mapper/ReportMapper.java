package com.smartcity.urban_management.modules.report.mapper;

import com.smartcity.urban_management.modules.report.dto.ReportResponse;
import com.smartcity.urban_management.modules.report.entity.Report;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    public ReportResponse toResponse(Report report) {

        Point point = report.getLocation();

        return ReportResponse.builder()
                .id(report.getId())
                .title(report.getTitle())
                .description(report.getDescription())
                .category(report.getCategory())
                .status(report.getStatus())
                .latitude(point.getY())
                .longitude(point.getX())
                .address(report.getAddress())
                .createdAt(report.getCreatedAt())
                .build();
    }
}