package com.fluffyyarn.store.identity.infrastructure.adapter.out.user;

import com.fluffyyarn.store.identity.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// El JPA sirve para parametrizar el tipo de fila (UserEntity) y el tipo de la clave primaria
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

  // Spring Data deriva la consulta del nombre del método: SELECT ... WHERE username = ?
  Optional<UserEntity> findByUsername(String username);
}
