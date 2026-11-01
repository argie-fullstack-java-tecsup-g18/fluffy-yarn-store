package com.fluffyyarn.store.identity.application.port.in.user;

public interface ToggleUserStatusUseCase {
  void toggleUserStatus(ToggleUserStatusCommand cmd);
}
