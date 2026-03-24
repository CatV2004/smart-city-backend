package com.smartcity.urban_management.modules.dashboard.admin.service;


import com.smartcity.urban_management.modules.dashboard.admin.dto.response.DashboardStatisticsResponse;
import com.smartcity.urban_management.modules.dashboard.admin.dto.response.PriorityReportResponse;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final ReportRepository reportRepository;

    @Transactional(readOnly = true)
    public DashboardStatisticsResponse getDashboardStatistics() {
        LocalDateTime startOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        LocalDateTime startOfYesterday = startOfToday.minusDays(1);
        LocalDateTime endOfYesterday = endOfToday.minusDays(1);

        // Get counts for today
        Integer needsReviewToday = reportRepository.countByStatusAndDeletedAtIsNull(ReportStatus.NEEDS_REVIEW);
        Integer lowConfidenceToday = reportRepository.countByStatusAndDeletedAtIsNull(ReportStatus.LOW_CONFIDENCE);
        Integer inProgressToday = reportRepository.countByStatusInAndDeletedAtIsNull(
                List.of(ReportStatus.ASSIGNED, ReportStatus.IN_PROGRESS)
        );
        Integer resolvedToday = reportRepository.countByStatusAndCreatedAtBetweenAndDeletedAtIsNull(
                ReportStatus.RESOLVED, startOfToday, endOfToday
        );
        System.out.println("resolvedToday: " + resolvedToday);

        // Get counts for yesterday
        Integer needsReviewYesterday = reportRepository.countByStatusAndCreatedAtBetweenAndDeletedAtIsNull(
                ReportStatus.NEEDS_REVIEW, startOfYesterday, endOfYesterday
        );
        Integer lowConfidenceYesterday = reportRepository.countByStatusAndCreatedAtBetweenAndDeletedAtIsNull(
                ReportStatus.LOW_CONFIDENCE, startOfYesterday, endOfYesterday
        );
        Integer inProgressYesterday = reportRepository.countByStatusInAndCreatedAtBetweenAndDeletedAtIsNull(
                List.of(ReportStatus.ASSIGNED, ReportStatus.IN_PROGRESS), startOfYesterday, endOfYesterday
        );
        Integer resolvedYesterday = reportRepository.countByStatusAndCreatedAtBetweenAndDeletedAtIsNull(
                ReportStatus.RESOLVED, startOfYesterday, endOfYesterday
        );

        // Calculate trends
        Double needsReviewTrend = calculateTrend(needsReviewToday, needsReviewYesterday);
        Double lowConfidenceTrend = calculateTrend(lowConfidenceToday, lowConfidenceYesterday);
        Double inProgressTrend = calculateTrend(inProgressToday, inProgressYesterday);
        Double resolvedTodayTrend = calculateTrend(resolvedToday, resolvedYesterday);

        // Get workload distribution
        Map<String, Integer> workloadDistribution = getWorkloadDistribution();

        // Get recent activities
        List<DashboardStatisticsResponse.RecentActivity> recentActivities = getRecentActivities();

        // Get total active reports
        Integer totalActive = reportRepository.countByDeletedAtIsNull();

        return DashboardStatisticsResponse.builder()
                .needsReview(needsReviewToday)
                .lowConfidence(lowConfidenceToday)
                .inProgress(inProgressToday)
                .resolvedToday(resolvedToday)
                .totalActive(totalActive)
                .needsReviewTrend(needsReviewTrend)
                .lowConfidenceTrend(lowConfidenceTrend)
                .inProgressTrend(inProgressTrend)
                .resolvedTodayTrend(resolvedTodayTrend)
                .workloadDistribution(workloadDistribution)
                .recentActivities(recentActivities)
                .build();
    }

    @Transactional(readOnly = true)
    public Page<PriorityReportResponse> getPriorityReports(Pageable pageable) {
        List<ReportStatus> priorityStatuses = List.of(
                ReportStatus.NEEDS_REVIEW,
                ReportStatus.LOW_CONFIDENCE
        );

        return reportRepository.findByStatusInAndDeletedAtIsNull(priorityStatuses, pageable)
                .map(this::mapToPriorityReportResponse);
    }

    private PriorityReportResponse mapToPriorityReportResponse(Report report) {
        return PriorityReportResponse.builder()
                .id(report.getId())
                .title(report.getTitle())
                .status(report.getStatus().name())
                .confidence(report.getAiConfidence())
                .priority(report.getPriority() != null ? report.getPriority().name() : "MEDIUM")
                .address(report.getAddress())
                .createdAt(report.getCreatedAt())
                .createdByName(report.getCreatedBy().getFullName())
                .build();
    }

    private Double calculateTrend(Integer today, Integer yesterday) {
        if (yesterday == 0) {
            return today > 0 ? 100.0 : 0.0;
        }
        return ((double) (today - yesterday) / yesterday) * 100;
    }

    private Map<String, Integer> getWorkloadDistribution() {
        Map<String, Integer> distribution = new HashMap<>();

        for (ReportStatus status : ReportStatus.values()) {
            Integer count = reportRepository.countByStatusAndDeletedAtIsNull(status);
            distribution.put(status.name(), count);
        }

        return distribution.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private List<DashboardStatisticsResponse.RecentActivity> getRecentActivities() {
        // Query recent activities from activity log table
        // This is a simplified version - you should have an ActivityLog entity
        List<DashboardStatisticsResponse.RecentActivity> activities = new ArrayList<>();

        // Example: Get recent verified reports
        List<Report> recentVerified = reportRepository.findTop5ByStatusAndDeletedAtIsNullOrderByUpdatedAtDesc(
                ReportStatus.VERIFIED
        );
        recentVerified.forEach(report -> {
            activities.add(DashboardStatisticsResponse.RecentActivity.builder()
                    .type("VERIFIED")
                    .message(String.format("Report #%s verified", report.getId().toString().substring(0, 8)))
                    .timestamp(report.getUpdatedAt().toString())
                    .reportId(report.getId().toString())
                    .reportTitle(report.getTitle())
                    .build());
        });

        // Get recent assigned reports
        List<Report> recentAssigned = reportRepository.findTop5ByStatusInAndDeletedAtIsNullOrderByUpdatedAtDesc(
                List.of(ReportStatus.ASSIGNED)
        );
        recentAssigned.forEach(report -> {
            activities.add(DashboardStatisticsResponse.RecentActivity.builder()
                    .type("ASSIGNED")
                    .message(String.format("Report #%s assigned to team", report.getId().toString().substring(0, 8)))
                    .timestamp(report.getUpdatedAt().toString())
                    .reportId(report.getId().toString())
                    .reportTitle(report.getTitle())
                    .build());
        });

        // Get recent flagged reports (low confidence or needs review)
        List<Report> recentFlagged = reportRepository.findTop5ByStatusInAndDeletedAtIsNullOrderByUpdatedAtDesc(
                List.of(ReportStatus.NEEDS_REVIEW, ReportStatus.LOW_CONFIDENCE)
        );
        recentFlagged.forEach(report -> {
            String message = report.getStatus() == ReportStatus.LOW_CONFIDENCE
                    ? String.format(
                    "AI flagged mismatch in report #%s (confidence: %.0f%%)",
                    report.getId().toString().substring(0, 8),
                    (report.getAiConfidence() != null ? report.getAiConfidence() : 0.0) * 100
            )
                    : String.format("Report #%s needs review", report.getId().toString().substring(0, 8));

            activities.add(DashboardStatisticsResponse.RecentActivity.builder()
                    .type("FLAGGED")
                    .message(message)
                    .timestamp(report.getUpdatedAt().toString())
                    .reportId(report.getId().toString())
                    .reportTitle(report.getTitle())
                    .build());
        });

        // Sort by timestamp and limit to 10
        return activities.stream()
                .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                .limit(10)
                .collect(Collectors.toList());
    }
}