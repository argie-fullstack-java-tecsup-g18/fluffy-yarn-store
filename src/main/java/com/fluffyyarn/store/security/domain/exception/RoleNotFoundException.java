package com.fluffyyarn.store.security.domain.exception;

// extiende de RuntimeException
public class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException(String message) {
    super(message);
  }
}
