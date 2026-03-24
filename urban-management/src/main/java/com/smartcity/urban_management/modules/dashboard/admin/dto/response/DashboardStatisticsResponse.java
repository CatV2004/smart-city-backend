package com.smartcity.urban_management.modules.dashboard.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatisticsResponse {
    private Integer needsReview;
    private Integer lowConfidence;
    private Integer inProgress;
    private Integer resolvedToday;
    private Integer totalActive;

    private Double needsReviewTrend;
    private Double lowConfidenceTrend;
    private Double inProgressTrend;
    private Double resolvedTodayTrend;

    private Map<String, Integer> workloadDistribution;
    private List<RecentActivity> recentActivities;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentActivity {
        private String type; // VERIFIED, ASSIGNED, FLAGGED
        private String message;
        private String timestamp;
        private String reportId;
        private String reportTitle;
    }
}

