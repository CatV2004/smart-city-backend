package com.smartcity.urban_management.shared.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        String code,
        String message
) {}