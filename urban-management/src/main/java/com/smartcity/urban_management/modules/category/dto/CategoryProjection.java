package com.smartcity.urban_management.modules.category.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CategoryProjection {
    UUID getId();

    String getName();

    String getSlug();

    String getDescription();

    String getIcon();

    String getColor();

    String getAiClass();

    boolean getActive();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}