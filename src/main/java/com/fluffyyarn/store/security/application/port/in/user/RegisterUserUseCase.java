package com.fluffyyarn.store.security.application.port.in.user;

import com.fluffyyarn.store.security.domain.model.user.User;

// PARA EL CUSTOMER
public interface RegisterUserUseCase {
  User registerUser(RegisterUserCommand cmd);
}
