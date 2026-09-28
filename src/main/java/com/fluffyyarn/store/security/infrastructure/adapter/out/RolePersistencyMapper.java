package com.fluffyyarn.store.security.infrastructure.adapter.out;

import com.fluffyyarn.store.security.domain.model.Role;
import com.fluffyyarn.store.security.infrastructure.entities.RoleEntity;

public class RolePersistencyMapper {
  public static RoleEntity toRoleEntity(Role role) {
    RoleEntity roleEntity = new RoleEntity(
        role.getName(),
        role.getDescription(),
        role.getCreatedAt());
    roleEntity.setId(role.getId());
    return roleEntity;
  }

  public static Role toRole(RoleEntity roleEntity) {
    return new Role(roleEntity.getId(), roleEntity.getName(), roleEntity.getDescription(), roleEntity.getCreatedAt());
  }
}
