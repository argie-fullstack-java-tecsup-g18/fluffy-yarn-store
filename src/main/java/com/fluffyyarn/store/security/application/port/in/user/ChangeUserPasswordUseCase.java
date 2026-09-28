package com.fluffyyarn.store.security.application.port.in.user;

public interface ChangeUserPasswordUseCase {
  void changeUserPassword(ChangeUserPasswordCommand cmd);
}
