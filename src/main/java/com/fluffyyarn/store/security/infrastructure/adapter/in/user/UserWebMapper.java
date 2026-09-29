package com.fluffyyarn.store.security.infrastructure.adapter.in.user;

import com.fluffyyarn.store.security.application.port.in.user.*;
import com.fluffyyarn.store.security.domain.model.user.User;

import java.util.List;

// Transforma entre el DTO (lo que viaja por HTTP) y los Commands (lo que pide la aplicación).
// El DTO nunca toca la base de datos y los Commands nunca se exponen al cliente.
public class UserWebMapper {

  // Oculta el hash para que no viaje al cliente
  public static UserResponseDto toUserResponseDto(User user) {
    return new UserResponseDto(
        user.getId(),
        user.getUsername(),
        user.getIsEnabled(),
        user.getRole() != null ? user.getRole().getName() : null,
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }

  public static List<UserResponseDto> toUserResponseDtoList(List<User> users) {
    return users.stream().map(UserWebMapper::toUserResponseDto).toList();
  }

  public static CreateUserCommand toCreateCommand(UserDto request) {
    return CreateUserCommand.builder()
        .username(request.getUsername())
        .password(request.getPassword())
        .roleName(request.getRoleName())
        .build();
  }

  public static RegisterUserCommand toRegisterCommand(UserDto request) {
    return RegisterUserCommand.builder()
        .username(request.getUsername())
        .password(request.getPassword())
        .build();
  }

  public static AuthenticateUserCommand toAuthenticateCommand(UserDto request) {
    return AuthenticateUserCommand.builder()
        .username(request.getUsername())
        .password(request.getPassword())
        .build();
  }

  public static ChangeUserPasswordCommand toChangePasswordCommand(UserDto request) {
    return ChangeUserPasswordCommand.builder()
        .username(request.getUsername())
        .newPassword(request.getPassword())
        .build();
  }

  public static ToggleUserStatusCommand toToggleStatusCommand(UserDto request) {
    return ToggleUserStatusCommand.builder()
        .username(request.getUsername())
        .isEnabled(request.getIsEnabled())
        .build();
  }
}