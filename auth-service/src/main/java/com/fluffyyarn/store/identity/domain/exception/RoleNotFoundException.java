package com.fluffyyarn.store.identity.domain.exception;

// extiende de RuntimeException
public class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException(String message) {
    super(message);
  }
}
