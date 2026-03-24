package com.smartcity.urban_management.modules.department.dto.office;

import java.util.UUID;

public record AddUserToOfficeRequest(
        UUID userId
) {}