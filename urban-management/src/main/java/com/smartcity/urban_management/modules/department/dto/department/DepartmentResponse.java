package com.smartcity.urban_management.modules.department.dto.department;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponse {

    private UUID id;
    private String name;
    private String code;
}