package com.smartcity.urban_management.modules.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class ReportCreateRequest {

    @Schema(example = "Ổ gà trên đường")
    private String title;

    @Schema(example = "Đường bị hư hỏng nghiêm trọng")
    private String description;

    @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID categoryId;

    @Schema(example = "10.7769")
    private Double latitude;

    @Schema(example = "106.7009")
    private Double longitude;

    @Schema(example = "92a Nguyễn Trãi, Phường Nguyễn Cư Trinh, Quận 1, TP.HCM")
    private String address;
}