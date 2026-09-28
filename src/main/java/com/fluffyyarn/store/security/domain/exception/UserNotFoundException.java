package com.fluffyyarn.store.security.domain.exception;

// extiende de RuntimeException
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}