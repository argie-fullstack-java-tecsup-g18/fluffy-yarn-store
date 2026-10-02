package com.fluffyyarn.store.identity.application.port.in.user;

public interface ChangeUserPasswordUseCase {
  void changeUserPassword(ChangeUserPasswordCommand cmd);
}
