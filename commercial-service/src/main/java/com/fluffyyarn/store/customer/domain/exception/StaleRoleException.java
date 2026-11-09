package com.fluffyyarn.store.customer.domain.exception;

/**
 * El llamador ya no puede demostrar (contra la BD de auth-service) que sigue
 * siendo ADMIN habilitado: el rol cambió, la cuenta se deshabilitó, el usuario
 * desapareció o auth-service negó el token. Token vigente pero con datos
 * desactualizados: la respuesta es 403 y hay que volver a iniciar sesión.
 */
public class StaleRoleException extends RuntimeException {

  public StaleRoleException(String message) {
    super(message);
  }
}
