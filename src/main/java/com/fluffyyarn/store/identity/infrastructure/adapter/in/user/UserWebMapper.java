package com.fluffyyarn.store.identity.infrastructure.adapter.in.user;

import com.fluffyyarn.store.identity.application.port.in.user.*;
import com.fluffyyarn.store.identity.domain.model.user.User;

import java.util.List;

// Transforma entre el DTO (lo que viaja por HTTP) y los Commands (lo que pide la aplicación).
// El DTO nunca toca la base de datos y los Commands nunca se exponen al cliente.
public class UserWebMapper {

  // Oculta el hash para que no viaje al cliente
  public static UserResponseDto toUserResponseDto(User user) {
    return new UserResponseDto(
        user.getId(),
        user.getUsername(),
        user.isEnabled(),
        user.getRole() != null ? user.getRole().getName() : null,
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }

  public static List<UserResponseDto> toUserResponseDtoList(List<User> users) {
    return users.stream().map(UserWebMapper::toUserResponseDto).toList();
  }

  public static CreateUserCommand toCreateCommand(CreateUserDto request) {
    return CreateUserCommand.builder()
        .username(request.getUsername())
        .password(request.getPassword())
        .roleName(request.getRoleName())
        .build();
  }

  public static RegisterUserCommand toRegisterCommand(RegisterUserDto request) {
    return RegisterUserCommand.builder()
        .username(request.getUsername())
        .password(request.getPassword())
        .build();
  }

  public static AuthenticateUserCommand toAuthenticateCommand(AuthenticateUserDto request) {
    return AuthenticateUserCommand.builder()
        .username(request.getUsername())
        .password(request.getPassword())
        .build();
  }

  public static ChangeUserPasswordCommand toChangePasswordCommand(ChangePasswordDto request) {
    return ChangeUserPasswordCommand.builder()
        .username(request.getUsername())
        .newPassword(request.getPassword())
        .build();
  }

  public static ToggleUserStatusCommand toToggleStatusCommand(ToggleStatusDto request) {
    return ToggleUserStatusCommand.builder()
        .username(request.getUsername())
        .isEnabled(request.isEnabled())
        .build();
  }
}