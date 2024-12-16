package com.sena.taskmanager.config;

import com.sena.taskmanager.dto.ApiErrorDto;
import com.sena.taskmanager.exceptions.BadRequestException;
import com.sena.taskmanager.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorDto> notFound(NotFoundException e) {
        ApiErrorDto apiError =
                new ApiErrorDto(
                        "not_found", e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiErrorDto> badRequestException(BadRequestException e) {
        ApiErrorDto apiError =
                new ApiErrorDto("bad_request", e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ApiErrorDto> internalServerError(Exception e) {
        LOGGER.error("Internal server error", e);
        ApiErrorDto apiError =
                new ApiErrorDto(
                        "internal_server_error",
                        e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
