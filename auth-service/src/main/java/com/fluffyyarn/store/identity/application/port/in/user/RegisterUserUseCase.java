package com.fluffyyarn.store.identity.application.port.in.user;

import com.fluffyyarn.store.identity.domain.model.user.User;

// PARA EL CUSTOMER
public interface RegisterUserUseCase {
  User registerUser(RegisterUserCommand cmd);
}
