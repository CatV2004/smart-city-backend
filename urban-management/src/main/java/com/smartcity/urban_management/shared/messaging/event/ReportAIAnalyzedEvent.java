package com.smartcity.urban_management.shared.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportAIAnalyzedEvent {

    private UUID reportId;

    private String label;
    private double confidence;
}
