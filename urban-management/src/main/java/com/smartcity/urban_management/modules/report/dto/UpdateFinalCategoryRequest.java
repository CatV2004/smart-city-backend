package com.smartcity.urban_management.modules.report.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class UpdateFinalCategoryRequest {

    private FinalCategoryType type;

    private UUID categoryId; // dùng khi MANUAL

    private String note;
}

