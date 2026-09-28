package com.fluffyyarn.store.security.infrastructure.adapter.in;

import com.fluffyyarn.store.security.application.port.in.CreateRoleUseCase;
import com.fluffyyarn.store.security.application.port.in.GetRoleUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Aquí van las notaciones @RestController para las rutas. Es el que se encarga de llamar a la aplicacion y pedir los parámetros que se necesitan.
@RestController
@RequestMapping("/roles")
public class RoleController {

  private final CreateRoleUseCase createRoleUseCase;
  private final GetRoleUseCase getRoleUseCase;

  // El controller necesita un puerto de entrada (useCases/interfaces)
  public RoleController(
      CreateRoleUseCase createRoleUseCase,
      GetRoleUseCase getRoleUseCase
  ) {
    this.createRoleUseCase = createRoleUseCase;
    this.getRoleUseCase = getRoleUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RoleDto create(@Valid @RequestBody RoleDto request) {
    return RoleWebMapper.toRoleDto(this.createRoleUseCase.create(
        RoleWebMapper.toCommand(request)));
  }

  @GetMapping
  public List<RoleDto> findAll() {
    return RoleWebMapper.toRoleDtoList(this.getRoleUseCase.findAll());
  }

  @GetMapping("/{id}")
  public RoleDto findById(@PathVariable Long id) {
    return RoleWebMapper.toRoleDto(this.getRoleUseCase.findById(id));
  }
}
