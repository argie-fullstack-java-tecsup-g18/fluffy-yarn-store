package com.fluffyyarn.store.security.infrastructure.adapter.in;

import com.fluffyyarn.store.security.application.port.in.CreateRoleCommand;
import com.fluffyyarn.store.security.domain.model.Role;

import java.util.List;

// (TODO: Revisar este comentario) Esta es la clase que se encarga de formatear los modelos a las respuestas que deben dar los endpoints al cliente.
public class RoleWebMapper {

  public static RoleDto toRoleDto(Role role) {
    return new RoleDto(
        role.getId(),
        role.getName(),
        role.getDescription(),
        role.getCreatedAt()
    );
  }

  public static List<RoleDto> toRoleDtoList(List<Role> roles) {
    return roles.stream().map(RoleWebMapper::toRoleDto).toList();
  }

  public static CreateRoleCommand toCommand(RoleDto request) {
    return new CreateRoleCommand(request.getName(), request.getDescription());

  }
}
