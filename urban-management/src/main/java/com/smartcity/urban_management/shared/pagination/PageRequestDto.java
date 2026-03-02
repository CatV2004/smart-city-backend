package com.smartcity.urban_management.shared.pagination;

import lombok.Data;

@Data
public class PageRequestDto {

    private Integer page = 0;
    private Integer size = 10;
    private String sort = "createdAt,desc";

}