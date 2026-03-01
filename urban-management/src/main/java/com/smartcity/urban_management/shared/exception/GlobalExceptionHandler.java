package com.smartcity.urban_management.shared.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {

        ErrorCode code = ex.getErrorCode();

        return ResponseEntity
                .status(code.getStatus())
                .body(new ErrorResponse(
                        LocalDateTime.now(),
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
                        LocalDateTime.now(),
                        code.getCode(),
                        code.getMessage()
                ));
    }

    @ExceptionHandler(org.springframework.security.authorization.AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AuthorizationDeniedException ex) {

        ErrorCode code = ErrorCode.FORBIDDEN;

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        code.getCode(),
                        code.getMessage()
                ));
    }
}