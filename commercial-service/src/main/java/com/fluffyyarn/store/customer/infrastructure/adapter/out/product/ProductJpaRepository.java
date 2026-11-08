package com.fluffyyarn.store.customer.infrastructure.adapter.out.product;

import com.fluffyyarn.store.customer.infrastructure.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> findBySku(String sku);

  boolean existsBySku(String sku);
}
