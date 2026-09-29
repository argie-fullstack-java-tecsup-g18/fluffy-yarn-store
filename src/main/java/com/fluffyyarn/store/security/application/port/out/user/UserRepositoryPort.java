package com.fluffyyarn.store.security.application.port.out.user;

import com.fluffyyarn.store.security.domain.model.user.User;

import java.util.List;
import java.util.Optional;

// RepositoryPort: Declara los métodos que van a ser implementados por UserPersistencyAdapter para guardar y leer usuarios.
public interface UserRepositoryPort {
  User save(User user);

  List<User> findAll();

  Optional<User> findById(Integer id);

  Optional<User> findByUsername(String username);
}
