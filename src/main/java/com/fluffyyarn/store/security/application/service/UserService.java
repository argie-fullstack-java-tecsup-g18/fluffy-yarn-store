package com.fluffyyarn.store.security.application.service;

import com.fluffyyarn.store.security.application.port.in.user.*;
import com.fluffyyarn.store.security.application.port.out.user.UserRepositoryPort;
import com.fluffyyarn.store.security.domain.exception.UserNotFoundException;
import com.fluffyyarn.store.security.domain.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements RegisterUserUseCase, CreateUserUseCase, AuthenticateUserUseCase, ChangeUserPasswordUseCase, ToggleUserStatusUseCase, GetUserUseCase {

  UserRepositoryPort repository;

  public UserService(UserRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  public User authenticateUser(AuthenticateUserCommand cmd) {
    User user = new User();
    return this.repository.save(user);
  }

  @Override
  public void changeUserPassword(ChangeUserPasswordCommand cmd) {

  }

  @Override
  public User createUser(CreateUserCommand cmd) {
    return null;
  }

  @Override
  public User findById(Integer id) throws UserNotFoundException {
    return null;
  }

  @Override
  public User findByUsername(String username) throws UserNotFoundException {
    return null;
  }

  @Override
  public List<User> findAll() {
    return List.of();
  }

  @Override
  public User registerUser(RegisterUserCommand cmd) {
    return null;
  }

  @Override
  public void toggleUserStatus(ToggleUserStatusCommand cmd) {

  }
}
