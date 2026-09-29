package com.fluffyyarn.store.security.application.service;

import com.fluffyyarn.store.security.application.port.in.user.*;
import com.fluffyyarn.store.security.application.port.out.user.UserRepositoryPort;
import com.fluffyyarn.store.security.domain.exception.UserNotFoundException;
import com.fluffyyarn.store.security.domain.model.user.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public class UserService implements RegisterUserUseCase, CreateUserUseCase, AuthenticateUserUseCase, ChangeUserPasswordUseCase, ToggleUserStatusUseCase, GetUserUseCase {

  UserRepositoryPort repository;
  PasswordEncoder passwordEncoder;

  public UserService(UserRepositoryPort repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User authenticateUser(AuthenticateUserCommand cmd) {
    User user = this.repository.findByUsername(cmd.getUsername())
        .orElseThrow(() -> new UserNotFoundException(
            "User with username " + cmd.getUsername() + " not found."));
    if (!this.passwordEncoder.matches(cmd.getPassword(), user.getPassword())) {
      throw new BadCredentialsException("Invalid password.");
    }
    return user;
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
    return this.repository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
  }

  @Override
  public User findByUsername(String username) throws UserNotFoundException {
    return this.repository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found."));
  }

  @Override
  public List<User> findAll() {
    return this.repository.findAll();
  }

  @Override
  public User registerUser(RegisterUserCommand cmd) {
    return null;
  }

  @Override
  public void toggleUserStatus(ToggleUserStatusCommand cmd) {
    User user = this.repository.findByUsername(cmd.getUsername())
        .orElseThrow(() -> new UserNotFoundException("User with username " + cmd.getUsername() + " not found."));
    user.setIsEnabled(cmd.getIsEnabled());
    this.repository.save(user);
  }
}
