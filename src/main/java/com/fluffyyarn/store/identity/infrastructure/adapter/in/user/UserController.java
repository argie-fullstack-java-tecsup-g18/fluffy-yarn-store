package com.fluffyyarn.store.identity.infrastructure.adapter.in.user;

import com.fluffyyarn.store.identity.application.port.in.user.*;
import com.fluffyyarn.store.identity.domain.model.user.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Los Controllers son adaptadores de ENTRADA
// Aquí van las notaciones @RestController para las rutas. Es el que se encarga de llamar a la aplicacion y pedir los parámetros que se necesitan.
@RestController
@RequestMapping("/users")
public class UserController {

  private final RegisterUserUseCase registerUserUseCase;
  private final CreateUserUseCase createUserUseCase;
  private final ChangeUserPasswordUseCase changeUserPasswordUseCase;
  private final ToggleUserStatusUseCase toggleUserStatusUseCase;
  private final GetUserUseCase getUserUseCase;
  private final UpdateUserUseCase updateUserUseCase;

  public UserController(RegisterUserUseCase registerUserUseCase, CreateUserUseCase createUserUseCase, ChangeUserPasswordUseCase changeUserPasswordUseCase, ToggleUserStatusUseCase toggleUserStatusUseCase, GetUserUseCase getUserUseCase, UpdateUserUseCase updateUserUseCase) {
    this.registerUserUseCase = registerUserUseCase;
    this.createUserUseCase = createUserUseCase;
    this.changeUserPasswordUseCase = changeUserPasswordUseCase;
    this.toggleUserStatusUseCase = toggleUserStatusUseCase;
    this.getUserUseCase = getUserUseCase;
    this.updateUserUseCase = updateUserUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponseDto register(@Valid @RequestBody RegisterUserDto request) {
    return UserWebMapper.toUserResponseDto(this.registerUserUseCase.registerUser(
        UserWebMapper.toRegisterCommand(request)));
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponseDto create(@Valid @RequestBody CreateUserDto request) {
    return UserWebMapper.toUserResponseDto(this.createUserUseCase.createUser(
        UserWebMapper.toCreateCommand(request)));
  }

  @PatchMapping("/password")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void changePassword(@Valid @RequestBody ChangePasswordDto request) {
    this.changeUserPasswordUseCase.changeUserPassword(
        UserWebMapper.toChangePasswordCommand(request));
  }

  @PatchMapping("/status")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void toggleStatus(@Valid @RequestBody ToggleStatusDto request) {
    this.toggleUserStatusUseCase.toggleUserStatus(
        UserWebMapper.toToggleStatusCommand(request));
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public UserResponseDto update(@PathVariable Integer id, @Valid @RequestBody UpdateUserDto request) {
    return UserWebMapper.toUserResponseDto(this.updateUserUseCase.update(
        UserWebMapper.toUpdateCommand(id, request)));
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserResponseDto> findAll() {
    return UserWebMapper.toUserResponseDtoList(this.getUserUseCase.findAll());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public UserResponseDto findById(@PathVariable Integer id) {
    return UserWebMapper.toUserResponseDto(this.getUserUseCase.findById(id));
  }

  @GetMapping("/username/{username}")
  @PreAuthorize("hasRole('ADMIN')")
  public UserResponseDto findByUsername(@PathVariable String username) {
    return UserWebMapper.toUserResponseDto(this.getUserUseCase.findByUsername(username));
  }
}
