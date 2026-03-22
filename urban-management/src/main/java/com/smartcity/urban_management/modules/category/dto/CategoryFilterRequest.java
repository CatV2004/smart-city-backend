package com.smartcity.urban_management.modules.category.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class CategoryFilterRequest {

    private String keyword;

    private Boolean active;

    private UUID departmentId;

}
