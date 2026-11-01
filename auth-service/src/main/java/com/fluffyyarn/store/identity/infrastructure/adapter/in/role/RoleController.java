package com.fluffyyarn.store.identity.infrastructure.adapter.in.role;

import com.fluffyyarn.store.identity.application.port.in.role.CreateRoleUseCase;
import com.fluffyyarn.store.identity.application.port.in.role.GetRoleUseCase;
import com.fluffyyarn.store.identity.application.port.in.role.UpdateRoleUseCase;
import com.fluffyyarn.store.identity.domain.model.role.RoleName;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Los Controllers son adaptadores de ENTRADA
// Aquí van las notaciones @RestController para las rutas. Es el que se encarga de llamar a la aplicacion y pedir los parámetros que se necesitan.
@RestController
@RequestMapping("/roles")
public class RoleController {

  private final CreateRoleUseCase createRoleUseCase;
  private final GetRoleUseCase getRoleUseCase;
  private final UpdateRoleUseCase updateRoleUseCase;

  // El controller necesita un puerto de entrada (useCases/interfaces)
  public RoleController(
      CreateRoleUseCase createRoleUseCase,
      GetRoleUseCase getRoleUseCase,
      UpdateRoleUseCase updateRoleUseCase
  ) {
    this.createRoleUseCase = createRoleUseCase;
    this.getRoleUseCase = getRoleUseCase;
    this.updateRoleUseCase = updateRoleUseCase;
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.CREATED)
  public RoleDto create(@Valid @RequestBody RoleDto request) {
    return RoleWebMapper.toRoleDto(this.createRoleUseCase.create(
        RoleWebMapper.toCommand(request)));
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public RoleDto update(@PathVariable Short id, @Valid @RequestBody UpdateRoleDto request) {
    return RoleWebMapper.toRoleDto(this.updateRoleUseCase.update(
        RoleWebMapper.toUpdateCommand(id, request)));
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<RoleDto> findAll() {
    return RoleWebMapper.toRoleDtoList(this.getRoleUseCase.findAll());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public RoleDto findById(@PathVariable Short id) {
    return RoleWebMapper.toRoleDto(this.getRoleUseCase.findById(id));
  }

  @GetMapping("/name/{name}")
  @PreAuthorize("hasRole('ADMIN')")
  public RoleDto findByName(@PathVariable RoleName name) {
    return RoleWebMapper.toRoleDto(this.getRoleUseCase.findByName(name));
  }
}
