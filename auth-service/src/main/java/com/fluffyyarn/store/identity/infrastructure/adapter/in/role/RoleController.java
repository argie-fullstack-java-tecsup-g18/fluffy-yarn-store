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
    return RoleWebMapper.toRoleDto(
        //Spring está redirigiendo esa llamada directamente al métod0 create de tu RoleService.
        this.createRoleUseCase.create(
            RoleWebMapper.toCommand(request)
        )
    );
  }
  //  luego de que esa funcion del controller haga toda esa conversion de request a un RoleDto
  //  se le envia el DTO al usuario
  // no veo donde entra el servicio aqui:
//  El servicio SÍ está ahí, oculto justo en el corazón de esa línea,
//  representado por this.createRoleUseCase.create(...).
//  Recuerda que createRoleUseCase es el Puerto de Entrada (la interfaz),
//  pero la variable this.createRoleUseCase en realidad está apuntando
//  por debajo a la clase de servicio (RoleServiceImpl),
//  que es la única que implementa ese puerto (nuestro Guardia Ejecutor).




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
