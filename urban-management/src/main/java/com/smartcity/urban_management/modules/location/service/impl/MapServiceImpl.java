package com.smartcity.urban_management.modules.location.service.impl;

import com.smartcity.urban_management.modules.category.repository.CategoryRepository;
import com.smartcity.urban_management.modules.department.repository.DepartmentRepository;
import com.smartcity.urban_management.modules.department.repository.DepartmentOfficeRepository;
import com.smartcity.urban_management.modules.location.dto.MapDataResponse;
import com.smartcity.urban_management.modules.location.dto.MapFeatureResponse;
import com.smartcity.urban_management.modules.location.dto.MapFilterRequest;
import com.smartcity.urban_management.modules.location.dto.projection.ReportMapProjection;
import com.smartcity.urban_management.modules.location.dto.projection.OfficeMapProjection;
import com.smartcity.urban_management.modules.location.dto.projection.TaskMapProjection;
import com.smartcity.urban_management.modules.location.service.MapService;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;

import com.smartcity.urban_management.modules.task.entity.TaskStatus;
import com.smartcity.urban_management.modules.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final ReportRepository reportRepository;
    private final CategoryRepository categoryRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentOfficeRepository officeRepository;
    private final TaskRepository taskRepository;

    @Override
    public MapDataResponse getMapData(MapFilterRequest filter) {

        List<MapFeatureResponse> features = new ArrayList<>();

        // ================= NORMALIZE =================
        List<String> statuses = normalizeStatuses(filter);
        List<UUID> categoryIds = normalizeCategoryIds(filter);
        List<UUID> departmentIds = normalizeDepartmentIds(filter);
        String keyword = normalizeKeyword(filter);

        // ================= REPORT =================
        if (Boolean.TRUE.equals(filter.getIncludeReports())) {
            features.addAll(buildReportFeatures(statuses, categoryIds, keyword));
        }

        // ================= OFFICE =================
        if (Boolean.TRUE.equals(filter.getIncludeOffices())) {
            features.addAll(buildOfficeFeatures(departmentIds, keyword));
        }

        return new MapDataResponse("FeatureCollection", features);
    }

    public MapDataResponse getTaskMapDataForStaff(MapFilterRequest filter, UUID userId) {
        List<MapFeatureResponse> features = new ArrayList<>();
        List<String> statuses = normalizeTaskStatuses(filter);
        String keyword = normalizeKeyword(filter);

        features.addAll(
                buildTaskFeaturesByUser(userId, statuses, keyword)
        );
        officeRepository.findOfficeByUserIdForMap(userId)
                .ifPresent(o -> features.add(buildOfficeFeature(o)));
        return new MapDataResponse("FeatureCollection", features);
    }

    // ================= NORMALIZE =================

    private List<String> normalizeStatuses(MapFilterRequest filter) {
        return (filter.getStatuses() == null || filter.getStatuses().isEmpty())
                ? Arrays.stream(ReportStatus.values()).map(Enum::name).toList()
                : filter.getStatuses().stream().map(Enum::name).toList();
    }

    private List<String> normalizeTaskStatuses(MapFilterRequest filter) {
        return (filter.getTaskStatuses() == null || filter.getTaskStatuses().isEmpty())
                ? Arrays.stream(TaskStatus.values()).map(Enum::name).toList()
                : filter.getTaskStatuses().stream().map(Enum::name).toList();
    }

    private List<UUID> normalizeCategoryIds(MapFilterRequest filter) {
        return (filter.getCategoryIds() == null || filter.getCategoryIds().isEmpty())
                ? categoryRepository.findAllIds()
                : filter.getCategoryIds();
    }

    private List<UUID> normalizeDepartmentIds(MapFilterRequest filter) {
        return (filter.getDepartmentIds() == null || filter.getDepartmentIds().isEmpty())
                ? departmentRepository.findAllIds()
                : filter.getDepartmentIds();
    }

    private String normalizeKeyword(MapFilterRequest filter) {
        return (filter.getKeyword() == null || filter.getKeyword().isBlank())
                ? null
                : filter.getKeyword().trim();
    }

    // ================= REPORT =================

    private List<MapFeatureResponse> buildReportFeatures(
            List<String> statuses,
            List<UUID> categoryIds,
            String keyword
    ) {

        List<ReportMapProjection> reports =
                reportRepository.findAllForMapFiltered(statuses, categoryIds, keyword);

        Map<UUID, List<String>> attachmentMap =
                buildAttachmentMap(reports, ReportMapProjection::getId);

        return reports.stream()
                .map(r -> new MapFeatureResponse(
                        "Feature",
                        buildReportProps(r, attachmentMap),
                        buildGeometry(r.getLng(), r.getLat())
                ))
                .toList();
    }

    private List<MapFeatureResponse> buildTaskFeaturesByUser(
            UUID userId,
            List<String> statuses,
            String keyword
    ) {

        List<TaskMapProjection> tasks =
                taskRepository.findAllForMapByUser(userId, statuses, keyword);

        Map<UUID, List<String>> attachmentMap =
                buildAttachmentMap(tasks, TaskMapProjection::getReportId);

        return tasks.stream()
                .map(t -> new MapFeatureResponse(
                        "Feature",
                        buildTaskProps(t, attachmentMap),
                        buildGeometry(t.getReportLng(), t.getReportLat())
                ))
                .toList();
    }

    private <T> Map<UUID, List<String>> buildAttachmentMap(
            List<T> items,
            Function<T, UUID> reportIdExtractor
    ) {

        List<UUID> reportIds = items.stream()
                .map(reportIdExtractor)
                .toList();

        if (reportIds.isEmpty()) return Map.of();

        return reportRepository.findAttachmentsByReportIds(reportIds)
                .stream()
                .collect(Collectors.groupingBy(
                        a -> a.getReportId(),
                        Collectors.mapping(a -> a.getFileUrl(), Collectors.toList())
                ));
    }

    private Map<String, Object> buildReportProps(
            ReportMapProjection r,
            Map<UUID, List<String>> attachmentMap
    ) {
        Map<String, Object> props = new HashMap<>();

        props.put("type", "report");
        props.put("id", r.getId());
        props.put("title", r.getTitle());
        props.put("category", r.getCategoryName());
        props.put("aiConfidence", r.getAiConfidence());
        props.put("description", r.getDescription());
        props.put("status", r.getStatus());
        props.put("priority", r.getPriority());
        props.put("createdAt", r.getCreatedAt());
        props.put("updatedAt", r.getUpdatedAt());
        props.put("address", r.getAddress());
        props.put("images", attachmentMap.getOrDefault(r.getId(), List.of()));

        return props;
    }

    private Map<String, Object> buildTaskProps(
            TaskMapProjection t,
            Map<UUID, List<String>> attachmentMap
    ) {
        Map<String, Object> props = new HashMap<>();

        props.put("type", "task");
        props.put("id", t.getId());
        props.put("assignedUserName", t.getAssignedUserName());
        props.put("status", t.getStatus());
        props.put("reportId", t.getReportId());
        props.put("reportTitle", t.getReportTitle());
        props.put("reportDescription", t.getReportDescription());
        props.put("reportAddress", t.getReportAddress());
        props.put("reportImages", attachmentMap.getOrDefault(t.getReportId(), List.of()));
        props.put("assignedAt", t.getAssignedAt());
        props.put("completedAt", t.getCompletedAt());
        props.put("startedAt", t.getStartedAt());
        return props;
    }

    // ================= OFFICE =================

    private List<MapFeatureResponse> buildOfficeFeatures(
            List<UUID> departmentIds,
            String keyword
    ) {

        List<OfficeMapProjection> offices =
                officeRepository.findAllForMapFiltered(departmentIds, keyword);

        return offices.stream()
                .map(o -> new MapFeatureResponse(
                        "Feature",
                        buildOfficeProps(o),
                        buildGeometry(o.getLng(), o.getLat())
                ))
                .toList();
    }

    private MapFeatureResponse buildOfficeFeature(OfficeMapProjection o) {
        return new MapFeatureResponse(
                "Feature",
                buildOfficeProps(o),
                buildGeometry(o.getLng(), o.getLat())
        );
    }

    private Map<String, Object> buildOfficeProps(OfficeMapProjection o) {
        Map<String, Object> props = new HashMap<>();

        props.put("type", "office");
        props.put("id", o.getId());
        props.put("name", o.getName());
        props.put("address", o.getAddress());
        props.put("department", o.getDepartmentName());

        return props;
    }

    // ================= COMMON =================

    private MapFeatureResponse.Geometry buildGeometry(Double lng, Double lat) {
        return new MapFeatureResponse.Geometry(
                "Point",
                new double[]{lng, lat}
        );
    }
}