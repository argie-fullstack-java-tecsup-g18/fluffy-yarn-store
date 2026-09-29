package com.fluffyyarn.store.security.infrastructure.adapter.out.user;

import com.fluffyyarn.store.security.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

}
