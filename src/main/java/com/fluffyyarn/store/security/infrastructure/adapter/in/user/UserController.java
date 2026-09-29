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
  public UserDTO register(@Valid @RequestBody UserDTO request) {
    return UserWebMapper.toUserDTO(this.registerUserUseCase.registerUser(
        UserWebMapper.toRegisterCommand(request)));
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTO create(@Valid @RequestBody UserDTO request) {
    return UserWebMapper.toUserDTO(this.createUserUseCase.createUser(
        UserWebMapper.toCreateCommand(request)));
  }

  @PostMapping("/authenticate")
  public UserDTO authenticate(@Valid @RequestBody UserDTO request) {
    return UserWebMapper.toUserDTO(this.authenticateUserUseCase.authenticateUser(
        UserWebMapper.toAuthenticateCommand(request)));
  }

  @PatchMapping("/password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void changePassword(@Valid @RequestBody UserDTO request) {
    this.changeUserPasswordUseCase.changeUserPassword(
        UserWebMapper.toChangePasswordCommand(request));
  }

  @PatchMapping("/status")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void toggleStatus(@Valid @RequestBody UserDTO request) {
    this.toggleUserStatusUseCase.toggleUserStatus(
        UserWebMapper.toToggleStatusCommand(request));
  }

  @GetMapping
  public List<UserDTO> findAll() {
    return UserWebMapper.toUserDTOList(this.getUserUseCase.findAll());
  }

  @GetMapping("/{id}")
  public UserDTO findById(@PathVariable Integer id) {
    return UserWebMapper.toUserDTO(this.getUserUseCase.findById(id));
  }

  @GetMapping("/username/{username}")
  public UserDTO findByUsername(@PathVariable String username) {
    return UserWebMapper.toUserDTO(this.getUserUseCase.findByUsername(username));
  }
}
