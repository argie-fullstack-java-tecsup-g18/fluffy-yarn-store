package com.fluffyyarn.store.identity.infrastructure.adapter.in.user;

import com.fluffyyarn.store.identity.application.port.in.user.*;
import com.fluffyyarn.store.identity.domain.model.user.User;

import java.util.List;

// Transforma entre el DTO (lo que viaja por HTTP) y los Commands (lo que pide la aplicación).
// El DTO nunca toca la base de datos y los Commands nunca se exponen al cliente.
public class UserWebMapper {

  // Oculta el hash para que no viaje al cliente
  // Refactor, reemplazo de uso de new por patron builder para la consistencia
  public static UserResponseDto toUserResponseDto(User user) {
    return UserResponseDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .enabled(user.isEnabled())
        .roleName(user.getRole() != null ? user.getRole().getName() : null)
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
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

  public static ChangeUserPasswordCommand toChangePasswordCommand(ChangePasswordDto request) {
    return ChangeUserPasswordCommand.builder()
        .username(request.getUsername())
        .newPassword(request.getPassword())
        .build();
  }

  public static ToggleUserStatusCommand toToggleStatusCommand(Integer id, ToggleStatusDto request) {
    return ToggleUserStatusCommand.builder()
        .id(id)
        .isEnabled(request.isEnabled())
        .build();
  }

  public static UpdateUserCommand toUpdateCommand(Integer id, UpdateUserDto request) {
    return UpdateUserCommand.builder()
        .id(id)
        .username(request.getUsername())
        .roleName(request.getRoleName())
        .build();
  }
}