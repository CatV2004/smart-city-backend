package com.smartcity.urban_management.shared.exception;

public class UnauthorizedException extends AppException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}