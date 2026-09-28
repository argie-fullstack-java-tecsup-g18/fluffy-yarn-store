package com.fluffyyarn.store.security.infrastructure.adapter.out;

import com.fluffyyarn.store.security.domain.model.Role;
import com.fluffyyarn.store.security.infrastructure.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// extiende de JPA Repository - La DB no debe tocar modelos, debe tocar entidad
public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
}
