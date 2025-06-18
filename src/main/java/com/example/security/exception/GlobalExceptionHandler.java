package com.example.security.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// import org.springframework.core.Ordered;
// import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

// @RestControllerAdvice
// @Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 500 - Internal server error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerInternalServerError(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Terjadi kesalahan server", ex.getMessage());
    }

    // 403 - Forbidden
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handlerAccessDenied(AccessDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Aksses ditolak", ex.getMessage());
    }

    // 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Data tidak valid",
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    // 404 Not Found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFound(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Data tidak ditemukan", ex.getMessage());
    }

    // 405 Method Not Allowed
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, "Metode tidak diperbolehkan", ex.getMessage());
    }

    // 409 Conflict
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleConflict(DataIntegrityViolationException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Terjadi konflik data", ex.getMessage());
    }

    // 422 Unprocessable Entity
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleUnprocessableEntity(BindException ex) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Permintaan tidak dapat diproses", ex.getMessage());
    }

    private ResponseEntity<?> buildResponse(HttpStatus status, String message, String detail) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("detail", detail);

        return new ResponseEntity<>(body, status);
    }
}
