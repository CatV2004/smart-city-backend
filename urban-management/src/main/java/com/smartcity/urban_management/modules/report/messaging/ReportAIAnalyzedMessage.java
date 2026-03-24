package com.smartcity.urban_management.modules.report.messaging;

import lombok.Data;

import java.util.List;

@Data
public class ReportAIAnalyzedMessage {
    private String type;
    private String reportId;
    List<Prediction> predictions;
}