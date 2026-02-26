package com.smartcity.urban_management.shared.exception;

public class NotFoundException extends AppException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}