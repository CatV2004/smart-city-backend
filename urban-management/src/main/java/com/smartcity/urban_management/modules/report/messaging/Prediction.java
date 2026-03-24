package com.smartcity.urban_management.modules.report.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prediction {
    private String label;
    private double confidence;
}