package com.fluffyyarn.store.exception;

import com.fluffyyarn.store.identity.domain.exception.DuplicateResourceException;
import com.fluffyyarn.store.identity.domain.exception.RoleNotFoundException;
import com.fluffyyarn.store.identity.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

  // Path variables que no convierten: /roles/ADMIN contra /roles/{id} (Short), o
  // /users/cliente1 contra /users/{id} (Integer). Sin esto la excepcion cae en /error
  // y devuelve un 400 sin message, que en Postman no explica nada.
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Map<String, Object>> handleTypeMismatch(
      MethodArgumentTypeMismatchException ex) {
    String tipo = ex.getRequiredType() != null
        ? ex.getRequiredType().getSimpleName()
        : "el tipo esperado";

    return build(HttpStatus.BAD_REQUEST,
        "Parámetro '" + ex.getName() + "': no se puede convertir '"
            + ex.getValue() + "' a " + tipo);
  }

  // JSON que Jackson no puede deserializar: cuerpo malformado o valor que no entra en
  // el tipo (por ejemplo roleName = "NOEXISTE" fuera del enum RoleName). Se manda el
  // mensaje de la causa y no el stack trace, para que el cliente sepa qué valores acepta.
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, Object>> handleUnreadable(
      HttpMessageNotReadableException ex) {
    String causa = String.valueOf(ex.getMostSpecificCause().getMessage());

    // Jackson le agrega " at [Source: ...] (through reference chain: ...)" con los
    // nombres de las clases internas. Sirve el motivo, no esa cola.
    int cola = causa.indexOf(" at [Source:");
    if (cola > 0) {
      causa = causa.substring(0, cola);
    }

    return build(HttpStatus.BAD_REQUEST, "Cuerpo JSON inválido: " + causa.trim());
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
