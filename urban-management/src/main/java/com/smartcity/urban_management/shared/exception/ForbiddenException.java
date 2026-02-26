package com.smartcity.urban_management.shared.exception;

public class ForbiddenException extends AppException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
