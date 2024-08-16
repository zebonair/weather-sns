package com.weathernotification.weather_sns.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.getHttpStatus();

        logger.error("Error: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException e) {
        logger.error("Database error: {}", e.getMessage());
        String customMessage;
        String errorCode;
        HttpStatus httpStatus;

        if (e.getMessage().contains("users_email_key")) {
            customMessage = ErrorCode.DUPLICATE_EMAIL.getMessage();
            errorCode = ErrorCode.DUPLICATE_EMAIL.getCode();
            httpStatus = HttpStatus.CONFLICT;
        } else if (e.getMessage().contains("users_username_key")) {
            customMessage = ErrorCode.DUPLICATE_USERNAME.getMessage();
            errorCode = ErrorCode.DUPLICATE_USERNAME.getCode();
            httpStatus = HttpStatus.CONFLICT;
        } else {
            customMessage = e.getMessage();
            errorCode = ErrorCode.DATABASE_ERROR.getCode();
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        ErrorResponse errorResponse = new ErrorResponse(errorCode, customMessage);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        logger.error("Runtime error: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
