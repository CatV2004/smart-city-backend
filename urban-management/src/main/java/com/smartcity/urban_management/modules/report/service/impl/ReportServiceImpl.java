package com.smartcity.urban_management.modules.report.service.impl;

import com.smartcity.urban_management.modules.report.dto.CreateReportRequest;
import com.smartcity.urban_management.modules.report.dto.ReportResponse;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.mapper.ReportMapper;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.modules.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;
    private final ReportMapper mapper;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    public ReportResponse create(CreateReportRequest request, UUID userId) {

        Point point = geometryFactory.createPoint(
                new org.locationtech.jts.geom.Coordinate(
                        request.getLongitude(),
                        request.getLatitude()
                )
        );
        point.setSRID(4326);

        Report report = Report.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .status("PENDING")
                .location(point)
                .address(request.getAddress())
                .createdBy(userId)
                .createdAt(LocalDateTime.now())
                .build();

        return mapper.toResponse(repository.save(report));
    }

    @Override
    public List<ReportResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}