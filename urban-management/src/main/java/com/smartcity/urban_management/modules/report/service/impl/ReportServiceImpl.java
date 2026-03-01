package com.smartcity.urban_management.modules.report.service.impl;

import com.smartcity.urban_management.modules.report.dto.ReportCreateRequest;
import com.smartcity.urban_management.modules.report.dto.ReportDetailResponse;
import com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.UpdateReportStatusRequest;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.mapper.ReportMapper;
import com.smartcity.urban_management.modules.report.repository.AttachmentRepository;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.modules.report.service.ReportService;
import com.smartcity.urban_management.modules.user.entity.User;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;
    private final AttachmentRepository attachmentRepository;
    private final EntityManager entityManager;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    public ReportSummaryResponse create(ReportCreateRequest request, UUID userId) {

        Point point = geometryFactory.createPoint(
                new org.locationtech.jts.geom.Coordinate(
                        request.getLongitude(),
                        request.getLatitude()
                )
        );
        point.setSRID(4326);

        User userRef = entityManager.getReference(User.class, userId);

        Report report = Report.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .status(ReportStatus.PENDING)
                .location(point)
                .address(request.getAddress())
                .createdBy(userRef)
                .build();

        report = repository.save(report);

        return repository.findSummaryById(report.getId())
                .orElseThrow(() ->
                        new RuntimeException("Report not found after creation"));
    }

    @Override
    public List<ReportSummaryResponse> findAll() {
        return repository.findAllResponses();
    }

    @Override
    @Transactional(readOnly = true)
    public ReportDetailResponse findById(String reportId) {

        UUID id = UUID.fromString(reportId);

        ReportDetailResponse response =
                repository.findDetailById(id)
                        .orElseThrow(() ->
                                new AppException(ErrorCode.REPORT_NOT_FOUND));

        attachmentRepository.findFirstByReportId(id)
                .ifPresent(response::setAttachment);
        System.out.println(response.getAttachment());
        return response;
    }

    @Override
    @Transactional
    public ReportDetailResponse updateStatus(
            UUID reportId,
            UpdateReportStatusRequest request,
            UUID adminId
    ) {

        Report report = repository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        validateStatusTransition(report.getStatus(), request.getStatus());

        User adminRef = entityManager.getReference(User.class, adminId);

        report.setStatus(request.getStatus());
        report.setApprovedBy(adminRef);

        return repository.findDetailById(reportId)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void softDeleteReport(UUID reportId) {

        Report report = repository
                .findByIdAndDeletedAtIsNull(reportId)
                .orElseThrow(() ->
                        new AppException(ErrorCode.REPORT_NOT_FOUND));

        // ===== business validation =====
        validateDeleteBusiness(report);

        // ===== soft delete =====
        report.setDeletedAt(LocalDateTime.now());
        report.setUpdatedAt(LocalDateTime.now());

        attachmentRepository.softDeleteByReportId(reportId);
    }

    @Override
    @Transactional
    public void purgeDeletedReport(UUID id) {

        Report report = repository
                .findById(id)
                .orElseThrow(() ->
                        new AppException(ErrorCode.REPORT_NOT_FOUND));

        if (report.getDeletedAt() == null) {
            throw new AppException(ErrorCode.REPORT_NOT_SOFT_DELETED);
        }

        attachmentRepository.deleteByReportId(id);

        repository.delete(report);
    }

    private void validateStatusTransition(
            ReportStatus current,
            ReportStatus target
    ) {

        switch (current) {

            case PENDING -> {
                if (target != ReportStatus.APPROVED &&
                        target != ReportStatus.REJECTED &&
                        target != ReportStatus.CANCELLED) {
                    throw new AppException(ErrorCode.INVALID_REPORT_STATUS);
                }
            }

            case APPROVED -> {
                if (target != ReportStatus.IN_PROGRESS &&
                        target != ReportStatus.CANCELLED) {
                    throw new AppException(ErrorCode.INVALID_REPORT_STATUS);
                }
            }

            case IN_PROGRESS -> {
                if (target != ReportStatus.RESOLVED) {
                    throw new AppException(ErrorCode.INVALID_REPORT_STATUS);
                }
            }

            default -> throw new AppException(ErrorCode.REPORT_STATUS_CHANGE_NOT_ALLOWED);
        }
    }

    private void validateDeleteBusiness(Report report) {

        if (report.getDeletedAt() != null) {
            throw new AppException(ErrorCode.REPORT_ALREADY_DELETED);
        }

        if (report.getStatus() == ReportStatus.RESOLVED) {
            throw new AppException(ErrorCode.REPORT_DELETE_NOT_ALLOWED);
        }
    }
}