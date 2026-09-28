package com.fluffyyarn.store.security.application.service;

import com.fluffyyarn.store.security.application.port.in.CreateRoleCommand;
import com.fluffyyarn.store.security.application.port.in.CreateRoleUseCase;
import com.fluffyyarn.store.security.application.port.in.GetRoleUseCase;
import com.fluffyyarn.store.security.application.port.out.RoleRepositoryPort;
import com.fluffyyarn.store.security.domain.exception.RoleNotFoundException;
import com.fluffyyarn.store.security.domain.model.Role;
import com.fluffyyarn.store.security.domain.model.RoleName;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// El servicio hace las implementaciones de los casos de uso
@Service
public class RoleService implements CreateRoleUseCase, GetRoleUseCase {

  /* TODO: Revisar este comentario, solo responder porqué se tiene que hacer asi.
  Para implementar los casos de usos se tiene que llamar al puerto de salida y el puerto de salida me va a devolver el méto.do que quiero implementar. */
  RoleRepositoryPort repository;

  public RoleService(RoleRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  public Role create(CreateRoleCommand cmd) {
    Role role = new Role();
    role.setName(cmd.getName());
    role.setDescription(cmd.getDescription());
    return this.repository.save(role);
  }

  // TODO: Preguntarle al profe si también se puede ubicar aquí el throws RoleNotFoundException para que el método sepa que hace un throws en caso de que falle
  @Override
  public Role findById(Long roleId) throws RoleNotFoundException {
    Optional<Role> optionalRole = this.repository.findById(roleId);
    if (optionalRole.isEmpty())
      throw new RoleNotFoundException("Role with" + roleId + "not found.");
    return optionalRole.get();
  }

  @Override
  public Role findByName(RoleName roleName) {
    Optional<Role> optionalRole = this.repository.findByName(roleName);
    if (optionalRole.isEmpty())
      throw new RoleNotFoundException("Role with" + roleName + "not found.");
    return null;
  }

  @Override
  public List<Role> findAll() {
    return this.repository.findAll();
  }
}
