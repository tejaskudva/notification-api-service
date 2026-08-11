package com.notification.api.exception.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.notification.api.exception.AbstractException;
import com.notification.api.exception.ResourceNotFoundException;
import com.notification.api.exception.ValidationException;
import com.notification.api.utils.CommonUtils;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError er : e.getBindingResult().getFieldErrors()) {
            errors.put(er.getField(), er.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

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