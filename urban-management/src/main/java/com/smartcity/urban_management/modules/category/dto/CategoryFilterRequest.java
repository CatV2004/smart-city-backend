package com.smartcity.urban_management.modules.category.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoryFilterRequest {

    private String keyword;

    private Boolean active;

}
