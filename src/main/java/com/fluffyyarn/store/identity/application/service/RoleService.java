package com.fluffyyarn.store.identity.application.service;

import com.fluffyyarn.store.identity.application.port.in.role.CreateRoleCommand;
import com.fluffyyarn.store.identity.application.port.in.role.CreateRoleUseCase;
import com.fluffyyarn.store.identity.application.port.in.role.GetRoleUseCase;
import com.fluffyyarn.store.identity.application.port.in.role.UpdateRoleCommand;
import com.fluffyyarn.store.identity.application.port.in.role.UpdateRoleUseCase;
import com.fluffyyarn.store.identity.application.port.out.role.RoleRepositoryPort;
import com.fluffyyarn.store.identity.domain.exception.DuplicateResourceException;
import com.fluffyyarn.store.identity.domain.exception.RoleNotFoundException;
import com.fluffyyarn.store.identity.domain.model.role.Role;
import com.fluffyyarn.store.identity.domain.model.role.RoleName;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// El servicio hace las implementaciones de los casos de uso
// El servicio es el que orquesta.
@Service
public class RoleService implements CreateRoleUseCase, GetRoleUseCase, UpdateRoleUseCase {

  /* TODO: Revisar este comentario, solo responder porqué se tiene que hacer asi.
  Para implementar los casos de usos se tiene que llamar al puerto de salida y el puerto de salida me va a devolver el méto.do que quiero implementar. */
  private final RoleRepositoryPort repository;

  public RoleService(RoleRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  public Role create(CreateRoleCommand cmd) {
    if (this.repository.findByName(cmd.getName()).isPresent())
      throw new DuplicateResourceException(
          "Role with name " + cmd.getName() + " already exists.");

    Role role = new Role();
    role.setName(cmd.getName());
    role.setDescription(cmd.getDescription());
    return this.repository.save(role);
  }

  @Override
  public Role update(UpdateRoleCommand cmd) {
    Role role = this.findById(cmd.getId());

    // PATCH: lo que viene en null no se toca. Un body vacio deja el rol igual.
    if (cmd.getDescription() != null)
      role.setDescription(cmd.getDescription());

    return this.repository.save(role);
  }

  // TODO: Preguntarle al profe si también se puede ubicar aquí el throws RoleNotFoundException para que el método sepa que hace un throws en caso de que falle
  @Override
  public Role findById(Short roleId) throws RoleNotFoundException {
    Optional<Role> optionalRole = this.repository.findById(roleId);
    if (optionalRole.isEmpty())
      throw new RoleNotFoundException("Role with" + roleId + "not found.");
    return optionalRole.get();
  }

  @Override
  public Role findByName(RoleName roleName) {
    Optional<Role> optionalRole = this.repository.findByName(roleName);
    if (optionalRole.isEmpty())
      throw new RoleNotFoundException("Role with name " + roleName + " not found.");
    return optionalRole.get();
  }

  @Override
  public List<Role> findAll() {
    return this.repository.findAll();
  }
}
