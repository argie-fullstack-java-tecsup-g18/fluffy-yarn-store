package com.fluffyyarn.store.security.application.port.out.role;

import com.fluffyyarn.store.security.domain.model.role.Role;
import com.fluffyyarn.store.security.domain.model.role.RoleName;

import java.util.List;
import java.util.Optional;

// RepositoryPort: Declara los métodos que van a ser implementados por RolePersistencyAdapter para guardar y leer roles.
// El servicio es el que va a orquestar guardar y leer pero no debe saber que es MySQL.  Es el "qué se necesita del mundo exterior", no el "cómo".
// PORT = REPOSITORY

public interface RoleRepositoryPort {
  Role save(Role role);

  List<Role> findAll();

  Optional<Role> findById(Short roleId);

  Optional<Role> findByName(RoleName roleName);
}
