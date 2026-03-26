package com.smartcity.urban_management.modules.location.dto.projection;

import java.util.UUID;

public interface OfficeMapProjection {

    UUID getId();
    String getName();
    String getAddress();
    String getDepartmentName();

    Double getLat();
    Double getLng();
}