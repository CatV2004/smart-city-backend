package com.smartcity.urban_management.shared.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {

        ErrorCode code = ex.getErrorCode();

        return ResponseEntity
                .status(code.getStatus())
                .body(new ErrorResponse(
                        java.time.LocalDateTime.now(),
                        code.getCode(),
                        code.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknown(Exception ex) {

        log.error("Unhandled exception", ex);

        ErrorCode code = ErrorCode.INTERNAL_ERROR;

        return ResponseEntity
                .status(code.getStatus())
                .body(new ErrorResponse(
                        java.time.LocalDateTime.now(),
                        code.getCode(),
                        code.getMessage()
                ));
    }
}