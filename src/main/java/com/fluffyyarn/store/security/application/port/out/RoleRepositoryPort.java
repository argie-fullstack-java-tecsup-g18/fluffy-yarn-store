package com.fluffyyarn.store.security.application.port.out;

import com.fluffyyarn.store.security.domain.model.Role;
import com.fluffyyarn.store.security.domain.model.RoleName;

import java.util.List;
import java.util.Optional;

// (TODO revisar solo este comentario) Interfaz que define los métodos para salir a la DB - PORT = REPOSITORY
// Se relaciona mucho con CustomerPersistencyAdapter

public interface RoleRepositoryPort {
  Role save(Role role);

  List<Role> findAll();

  Optional<Role> findById(Long roleId);

  Optional<Role> findByName(RoleName roleName);
}
