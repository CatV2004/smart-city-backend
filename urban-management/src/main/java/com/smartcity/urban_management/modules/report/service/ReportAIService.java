package com.smartcity.urban_management.modules.report.service;

import com.smartcity.urban_management.infrastructure.redis.cache.ReportCacheService;
import com.smartcity.urban_management.modules.category.entity.Category;
import com.smartcity.urban_management.modules.category.repository.CategoryRepository;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.messaging.Prediction;
import com.smartcity.urban_management.modules.report.messaging.ReportAIAnalyzedMessage;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReportAIService {

    private final ReportRepository reportRepository;
    private final CategoryRepository categoryRepository;
    private final ReportCacheService reportCacheService;
    private final ReportService reportService;

    private static final double THRESHOLD = 0.7;

    public void processAIResult(ReportAIAnalyzedMessage message) {

        log.debug("[AI] Start processing | reportId={}", message.getReportId());

        // =========================
        // PARSE REPORT ID
        // =========================
        UUID reportId;
        try {
            reportId = UUID.fromString(message.getReportId());
        } catch (Exception e) {
            log.error("[AI] Invalid reportId format | value={}", message.getReportId(), e);
            return;
        }

        // =========================
        // FETCH REPORT
        // =========================
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> {
                    log.error("[AI] Report not found | reportId={}", reportId);
                    return new RuntimeException("Report not found");
                });

        ReportStatus finalStatus;
        Category aiCategory = null;
        Double confidence = null;

        // =========================
        // CASE 0: NO PREDICTION
        // =========================
        if (message.getPredictions() == null || message.getPredictions().isEmpty()) {

            log.warn("[AI] No predictions returned | reportId={}", reportId);

            finalStatus = ReportStatus.LOW_CONFIDENCE;

        } else {

            Prediction prediction = message.getPredictions().get(0);

            log.debug("[AI] Raw prediction | label={} | confidence={}",
                    prediction.getLabel(), prediction.getConfidence());

            aiCategory = categoryRepository
                    .findByAiClass(prediction.getLabel())
                    .orElseThrow(() -> {
                        log.error("[AI] Category not found | label={}", prediction.getLabel());
                        return new RuntimeException("Category not found");
                    });

            confidence = prediction.getConfidence();

            // ===== SET AI DATA =====
            report.setAiCategory(aiCategory);
            report.setAiConfidence(confidence);

            // =========================
            // CASE: USER CATEGORY NULL
            // =========================
            if (report.getUserCategory() == null) {

                log.warn("[AI] userCategory is null | reportId={}", reportId);

                finalStatus = ReportStatus.NEEDS_REVIEW;

            } else {

                boolean match = aiCategory.getId()
                        .equals(report.getUserCategory().getId());

                log.debug("[AI] Mapping | reportId={} | userCategory={} | aiCategory={} | match={} | confidence={}",
                        report.getId(),
                        report.getUserCategory().getName(),
                        aiCategory.getName(),
                        match,
                        confidence
                );

                // =========================
                // CASE 1: LOW CONFIDENCE
                // =========================
                if (confidence < THRESHOLD) {

                    finalStatus = ReportStatus.LOW_CONFIDENCE;

                }
                // =========================
                // CASE 2: MATCH + HIGH CONF
                // =========================
                else if (match) {

                    report.setFinalCategory(aiCategory);
                    finalStatus = ReportStatus.VERIFIED_AUTO;

                }
                // =========================
                // CASE 3: MISMATCH
                // =========================
                else {

                    finalStatus = ReportStatus.NEEDS_REVIEW;
                }
            }
        }

        // =========================
        // CLEAR AI DATA IF NEEDED
        // =========================
        if (aiCategory == null) {
            report.setAiCategory(null);
            report.setAiConfidence(null);
        }

        // =========================
        // SAVE AI DATA FIRST
        // =========================
        reportRepository.save(report);

        // =========================
        // UPDATE STATUS (WITH HISTORY)
        // =========================
        reportService.updateStatus(
                report,
                finalStatus,
                "SYSTEM_AI",
                buildNote(finalStatus, confidence)
        );

        log.debug("[AI] Updated status | reportId={} | status={}",
                report.getId(), finalStatus);

        // =========================
        // CACHE EVICTION
        // =========================
        reportCacheService.evictReport(report.getId());
        reportCacheService.evictAllReportPages();

        if (report.getCreatedBy() != null) {
            UUID userId = report.getCreatedBy().getId();
            reportCacheService.evictUserReportPages(userId);
            reportCacheService.evictRecentReports(userId);
        }

        log.debug("[AI] Finished processing | reportId={} | finalStatus={}",
                report.getId(),
                finalStatus
        );
    }

    private String buildNote(ReportStatus status, Double confidence) {

        if (status == ReportStatus.LOW_CONFIDENCE) {
            return "AI confidence too low: " + confidence;
        }

        if (status == ReportStatus.VERIFIED_AUTO) {
            return "AI verified automatically (confidence=" + confidence + ")";
        }

        if (status == ReportStatus.NEEDS_REVIEW) {
            return "AI result differs from user input";
        }

        return "AI processed";
    }
}