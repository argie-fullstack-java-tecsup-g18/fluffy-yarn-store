package com.fluffyyarn.store.identity.infrastructure.adapter.out.role;

import com.fluffyyarn.store.identity.domain.model.role.RoleName;
import com.fluffyyarn.store.identity.infrastructure.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// extiende de JPA Repository - La DB no debe tocar modelos, debe tocar entidad
public interface RoleJpaRepository extends JpaRepository<RoleEntity, Short> {
  Optional<RoleEntity> findByName(RoleName name);
}
