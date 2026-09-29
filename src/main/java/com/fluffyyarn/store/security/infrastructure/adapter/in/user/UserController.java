package com.fluffyyarn.store.security.infrastructure.adapter.in.user;

import com.fluffyyarn.store.security.application.port.in.user.*;
import com.fluffyyarn.store.security.domain.model.user.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Los Controllers son adaptadores de ENTRADA
// Aquí van las notaciones @RestController para las rutas. Es el que se encarga de llamar a la aplicacion y pedir los parámetros que se necesitan.
@RestController
@RequestMapping("/users")
public class UserController {

  private final RegisterUserUseCase registerUserUseCase;
  private final CreateUserUseCase createUserUseCase;
  private final AuthenticateUserUseCase authenticateUserUseCase;
  private final ChangeUserPasswordUseCase changeUserPasswordUseCase;
  private final ToggleUserStatusUseCase toggleUserStatusUseCase;
  private final GetUserUseCase getUserUseCase;

  public UserController(RegisterUserUseCase registerUserUseCase, CreateUserUseCase createUserUseCase, AuthenticateUserUseCase authenticateUserUseCase, ChangeUserPasswordUseCase changeUserPasswordUseCase, ToggleUserStatusUseCase toggleUserStatusUseCase, GetUserUseCase getUserUseCase) {
    this.registerUserUseCase = registerUserUseCase;
    this.createUserUseCase = createUserUseCase;
    this.authenticateUserUseCase = authenticateUserUseCase;
    this.changeUserPasswordUseCase = changeUserPasswordUseCase;
    this.toggleUserStatusUseCase = toggleUserStatusUseCase;
    this.getUserUseCase = getUserUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto register(@Valid @RequestBody UserDto request) {
    return UserWebMapper.toUserDto(this.registerUserUseCase.registerUser(
        UserWebMapper.toRegisterCommand(request)));
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto create(@Valid @RequestBody UserDto request) {
    return UserWebMapper.toUserDto(this.createUserUseCase.createUser(
        UserWebMapper.toCreateCommand(request)));
  }

  @PostMapping("/authenticate")
  public UserDto authenticate(@Valid @RequestBody UserDto request) {
    return UserWebMapper.toUserDto(this.authenticateUserUseCase.authenticateUser(
        UserWebMapper.toAuthenticateCommand(request)));
  }

  @PatchMapping("/password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void changePassword(@Valid @RequestBody UserDto request) {
    this.changeUserPasswordUseCase.changeUserPassword(
        UserWebMapper.toChangePasswordCommand(request));
  }

  @PatchMapping("/status")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void toggleStatus(@Valid @RequestBody UserDto request) {
    this.toggleUserStatusUseCase.toggleUserStatus(
        UserWebMapper.toToggleStatusCommand(request));
  }

  @GetMapping
  public List<UserDto> findAll() {
    return UserWebMapper.toUserDtoList(this.getUserUseCase.findAll());
  }

  @GetMapping("/{id}")
  public UserDto findById(@PathVariable Integer id) {
    return UserWebMapper.toUserDto(this.getUserUseCase.findById(id));
  }

  @GetMapping("/username/{username}")
  public UserDto findByUsername(@PathVariable String username) {
    return UserWebMapper.toUserDto(this.getUserUseCase.findByUsername(username));
  }
}
