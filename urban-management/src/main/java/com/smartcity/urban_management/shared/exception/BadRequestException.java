package com.smartcity.urban_management.shared.exception;

public class BadRequestException extends AppException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}