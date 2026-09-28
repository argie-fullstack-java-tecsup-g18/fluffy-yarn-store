package com.fluffyyarn.store.security.infrastructure.adapter.out.role;

import com.fluffyyarn.store.security.application.port.out.role.RoleRepositoryPort;
import com.fluffyyarn.store.security.domain.model.role.Role;
import com.fluffyyarn.store.security.domain.model.role.RoleName;
import com.fluffyyarn.store.security.infrastructure.entities.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

// Aquí es donde se implementa el RoleRepositoryPort, con todos sus métodos. Este sigue siendo un adaptador que implementa un repositorio. No es un repositorio.

@Component // TODO: especificar brevemente que hace y para que sirve
public class RolePersistencyAdapter implements RoleRepositoryPort {

  private final RoleJpaRepository roleJpaRepository;
  // Aquí también se inyecta el RoleJpaRepository

  public RolePersistencyAdapter(RoleJpaRepository roleJpaRepository) {
    this.roleJpaRepository = roleJpaRepository;
  }

  @Override
  public Role save(Role role) {
    RoleEntity entitySaved = roleJpaRepository.save(RolePersistencyMapper.toRoleEntity(role));
    return RolePersistencyMapper.toRole(entitySaved);
  }

  @Override
  public List<Role> findAll() {
    return roleJpaRepository.findAll().stream().map(RolePersistencyMapper::toRole).toList();
  }

  @Override
  public Optional<Role> findById(Long roleId) {
    return this.roleJpaRepository.findById(roleId).map(RolePersistencyMapper::toRole);
  }

  // TODO: implementar bien este método findByName
  @Override
  public Optional<Role> findByName(RoleName roleName) {
    return Optional.empty();
  }
}
