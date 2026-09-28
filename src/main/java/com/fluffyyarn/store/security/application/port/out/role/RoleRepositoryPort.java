package com.fluffyyarn.store.security.application.port.out.role;

import com.fluffyyarn.store.security.domain.model.role.Role;
import com.fluffyyarn.store.security.domain.model.role.RoleName;

import java.util.List;
import java.util.Optional;

// En el RepositoryPort se declara la necesidad de lo que se va a guardar como una interfaz. El servicio es el que va a orquestar guardar y leer [ero no debe saber que es MySQL.  Es el "qué se necesita del mundo exterior", no el "cómo".
// (TODO revisar solo este comentario) Interfaz que define los métodos para salir a la DB - PORT = REPOSITORY
// Se relaciona mucho con CustomerPersistencyAdapter

public interface RoleRepositoryPort {
  Role save(Role role);

  List<Role> findAll();

  Optional<Role> findById(Short roleId);

  Optional<Role> findByName(RoleName roleName);
}
