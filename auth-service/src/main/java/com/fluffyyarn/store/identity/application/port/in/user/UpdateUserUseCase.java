package com.fluffyyarn.store.identity.application.port.in.user;

import com.fluffyyarn.store.identity.domain.model.user.User;

public interface UpdateUserUseCase {
  User update(UpdateUserCommand cmd);
}
