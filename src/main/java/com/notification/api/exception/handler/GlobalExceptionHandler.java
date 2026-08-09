package com.notification.api.exception.handler;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.notification.api.exception.AbstractException;
import com.notification.api.exception.ResourceNotFoundException;
import com.notification.api.exception.ValidationException;
import com.notification.api.utils.CommonUtils;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException e) {
        return genericExceptionHandler(e, () -> ResponseEntity.badRequest().body(e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return genericExceptionHandler(e, () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
    }

    public ResponseEntity<String> genericExceptionHandler(final AbstractException e,
            final Supplier<ResponseEntity<String>> runner) {

        if (CommonUtils.isNotEmpty(e.getStatusCode())) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getErrorMessage());
        }
        return runner.get();
    }
}