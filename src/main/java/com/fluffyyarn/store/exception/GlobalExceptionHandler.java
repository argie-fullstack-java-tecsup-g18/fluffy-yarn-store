package com.fluffyyarn.store.exception;

import com.fluffyyarn.store.identity.domain.exception.DuplicateResourceException;
import com.fluffyyarn.store.identity.domain.exception.RoleNotFoundException;
import com.fluffyyarn.store.identity.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

// Este lo usa cualquier módulo
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
    return build(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(RoleNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleRoleNotFound(RoleNotFoundException ex) {
    return build(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateResourceException ex) {
    return build(HttpStatus.CONFLICT, ex.getMessage());
  }

  // Validaciones de los DTO (@NotBlank, @Size). Se mandan todos separados por "; " para
  // que el front no tenga que adivinar si fallaron dos campos.
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining("; "));

    return build(HttpStatus.BAD_REQUEST,
        message.isEmpty() ? "Datos inválidos" : message);
  }

  private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("message", message);
    return ResponseEntity.status(status).body(body);
  }
}
