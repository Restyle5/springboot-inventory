package com.example.inventory.exception;

import org.springframework.dao.DuplicateKeyException;
import tools.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        body.put("status", 400);
        body.put("errors", errors);

        log.warn("Validation failed: {}", errors);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(
            ResponseStatusException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", ex.getStatusCode().value());
        body.put("error", ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put("status", 500);
        body.put("error", "An unexpected error occurred");
        return ResponseEntity.internalServerError().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFormat(
            HttpMessageNotReadableException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ife) {
            String field = ife.getPath().get(0).getPropertyName();
            body.put("error", "Invalid value for field: " + field);
        } else {
            body.put("error", "Malformed JSON request");
        }

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateKey(DuplicateKeyException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("status", 409);

        String message = ex.getMessage();
        String field = "unknown";

        if (message != null && message.contains("index:")) {
            // extracts "name" from "index: name_1"
            String index = message.replaceAll(".*index: (\\w+)_\\d+.*", "$1");
            field = index;
        }

        body.put("error", "Duplicate entry for field: " + field);

        return ResponseEntity.status(409).body(body);
    }
}