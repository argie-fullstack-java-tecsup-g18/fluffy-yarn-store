package com.fluffyyarn.store.identity.application.service;

import com.fluffyyarn.store.identity.application.port.in.user.*;
import com.fluffyyarn.store.identity.application.port.out.role.RoleRepositoryPort;
import com.fluffyyarn.store.identity.application.port.out.user.UserRepositoryPort;
import com.fluffyyarn.store.identity.domain.exception.RoleNotFoundException;
import com.fluffyyarn.store.identity.domain.exception.UserNotFoundException;
import com.fluffyyarn.store.identity.domain.model.role.Role;
import com.fluffyyarn.store.identity.domain.model.role.RoleName;
import com.fluffyyarn.store.identity.domain.model.user.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements RegisterUserUseCase, CreateUserUseCase, AuthenticateUserUseCase, ChangeUserPasswordUseCase, ToggleUserStatusUseCase, GetUserUseCase {

private final UserRepositoryPort repository;
private final PasswordEncoder passwordEncoder;
private final RoleRepositoryPort roleRepository;

  public UserService(
      UserRepositoryPort repository,
      PasswordEncoder passwordEncoder,
      RoleRepositoryPort roleRepository) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  // Para admins
  @Override
  public User createUser(CreateUserCommand cmd) {
    Role role = this.roleRepository.findByName(cmd.getRoleName())
        .orElseThrow(() -> new RoleNotFoundException(
            "Role with name " + cmd.getRoleName() + " not found."));
    User user = User.builder()
        .username(cmd.getUsername())
        .password(this.passwordEncoder.encode(cmd.getPassword()))
        .isEnabled(true)
        .role(role)
        .build();
    return this.repository.save(user);
  }

  // Para customers
  @Override
  public User registerUser(RegisterUserCommand cmd) {
    // El rol lo decide el sistema, no el cliente: un registro público no puede
    // auto-asignarse ADMIN. Por eso el endpoint de registro no recibe roleName.
    Role role = this.roleRepository.findByName(RoleName.CUSTOMER)
        .orElseThrow(() -> new RoleNotFoundException(
            "Role with name " + RoleName.CUSTOMER + " not found."));
    User user = User.builder()
        .username(cmd.getUsername())
        .password(this.passwordEncoder.encode(cmd.getPassword()))
        .isEnabled(true)
        .role(role)
        .build();
    return this.repository.save(user);
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
    User user = this.repository.findByUsername(cmd.getUsername())
        .orElseThrow(() -> new UserNotFoundException(
            "User with username " + cmd.getUsername() + " not found."));
    user.setPassword(this.passwordEncoder.encode(cmd.getNewPassword()));
    this.repository.save(user);
  }

  @Override
  public void toggleUserStatus(ToggleUserStatusCommand cmd) {
    User user = this.repository.findByUsername(cmd.getUsername())
        .orElseThrow(() -> new UserNotFoundException("User with username " + cmd.getUsername() + " not found."));
    user.setEnabled(cmd.isEnabled());
    this.repository.save(user);
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
}
