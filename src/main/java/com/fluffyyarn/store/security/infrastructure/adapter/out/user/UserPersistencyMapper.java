package com.fluffyyarn.store.security.infrastructure.adapter.out.user;

import com.fluffyyarn.store.security.domain.model.role.Role;
import com.fluffyyarn.store.security.domain.model.user.User;
import com.fluffyyarn.store.security.infrastructure.entities.RoleEntity;
import com.fluffyyarn.store.security.infrastructure.entities.UserEntity;

public class UserPersistencyMapper {

  public static UserEntity toUserEntity(User user) {
    return new UserEntity(
        user.getUsername(),
        user.getPassword(),
        toRoleEntity(user.getRole()),
        user.getIsEnabled(),
        user.getCreatedAt(),
        user.getUpdatedAt());
  }

  public static User toUser(UserEntity userEntity) {
    return User.builder()
        .id(userEntity.getId())
        .username(userEntity.getUsername())
        .password(userEntity.getPassword())
        .isEnabled(userEntity.getIsEnabled())
        .role(toRole(userEntity.getRole()))
        .createdAt(userEntity.getCreatedAt())
        .updatedAt(userEntity.getUpdatedAt())
        .build();
  }

  // La entidad guarda la relación como RoleEntity, nunca como el modelo de dominio Role.
  private static RoleEntity toRoleEntity(Role role) {
    if (role == null) return null;
    RoleEntity roleEntity = new RoleEntity(role.getName(), role.getDescription(), role.getCreatedAt());
    roleEntity.setId(role.getId());
    return roleEntity;
  }

  private static Role toRole(RoleEntity roleEntity) {
    if (roleEntity == null) return null;
    return Role.builder()
        .id(roleEntity.getId())
        .name(roleEntity.getName())
        .description(roleEntity.getDescription())
        .createdAt(roleEntity.getCreatedAt())
        .build();
  }
}
