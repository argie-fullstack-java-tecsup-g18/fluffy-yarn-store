package com.fluffyyarn.store.identity.application.port.in.user;

import com.fluffyyarn.store.identity.domain.exception.UserNotFoundException;
import com.fluffyyarn.store.identity.domain.model.user.User;

import java.util.List;

public interface GetUserUseCase {
  User findById(Integer id) throws UserNotFoundException;

  User findByUsername(String username) throws UserNotFoundException;

  List<User> findAll();
}
