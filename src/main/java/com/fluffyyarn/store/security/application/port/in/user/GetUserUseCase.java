package com.fluffyyarn.store.security.application.port.in.user;

import com.fluffyyarn.store.security.domain.exception.UserNotFoundException;
import com.fluffyyarn.store.security.domain.model.user.User;

import java.util.List;

public interface GetUserUseCase {
  User findById(Integer id) throws UserNotFoundException;

  User findByUsername(String username) throws UserNotFoundException;

  List<User> findAll();
}
