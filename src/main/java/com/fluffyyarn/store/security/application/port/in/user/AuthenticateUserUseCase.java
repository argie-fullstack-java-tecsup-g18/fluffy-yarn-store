package com.fluffyyarn.store.security.application.port.in.user;

import com.fluffyyarn.store.security.domain.model.user.User;

public interface AuthenticateUserUseCase {
  User authenticateUser(AuthenticateUserCommand cmd);
}
