package com.fluffyyarn.store.security.infrastructure.adapter.out.user;

import com.fluffyyarn.store.security.application.port.out.user.UserRepositoryPort;
import com.fluffyyarn.store.security.domain.model.user.User;
import com.fluffyyarn.store.security.infrastructure.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

// Aquí es donde se implementa el UserRepositoryPort, con todos sus métodos. Este sigue siendo un adaptador que implementa un repositorio. No es un repositorio.

@Component // TODO: especificar brevemente que hace y para que sirve
public class UserPersistencyAdapter implements UserRepositoryPort {

  private final UserJpaRepository userJpaRepository;
  // Aquí también se inyecta el UserJpaRepository

  public UserPersistencyAdapter(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  @Override
  public User save(User user) {
    UserEntity entitySaved = userJpaRepository.save(UserPersistencyMapper.toUserEntity(user));
    return UserPersistencyMapper.toUser(entitySaved);
  }

  @Override
  public List<User> findAll() {
    return userJpaRepository.findAll().stream().map(UserPersistencyMapper::toUser).toList();
  }

  @Override
  public Optional<User> findById(Integer id) {
    return this.userJpaRepository.findById(id).map(UserPersistencyMapper::toUser);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return this.userJpaRepository.findByUsername(username).map(UserPersistencyMapper::toUser);
  }
}
