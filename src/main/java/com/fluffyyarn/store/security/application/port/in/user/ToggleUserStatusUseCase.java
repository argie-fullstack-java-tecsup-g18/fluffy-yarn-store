package com.fluffyyarn.store.security.application.port.in.user;

public interface ToggleUserStatusUseCase {
  void toggleUserStatus(ToggleUserStatusCommand cmd);
}
