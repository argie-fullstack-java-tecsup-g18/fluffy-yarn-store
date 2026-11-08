package com.fluffyyarn.store.customer.domain.exception;

public class DuplicateProductException extends RuntimeException {
  public DuplicateProductException(String message) {
    super(message);
  }
}
