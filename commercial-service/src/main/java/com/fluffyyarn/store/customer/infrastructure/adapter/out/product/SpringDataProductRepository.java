package com.fluffyyarn.store.customer.infrastructure.adapter.out.product;

import com.fluffyyarn.store.customer.application.port.out.product.ProductRepositoryPort;
import com.fluffyyarn.store.customer.domain.model.Product;
import com.fluffyyarn.store.customer.infrastructure.entities.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SpringDataProductRepository implements ProductRepositoryPort {

  private final ProductJpaRepository jpaRepository;

  public SpringDataProductRepository(ProductJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Product save(Product product) {
    ProductEntity entity = ProductPersistenceMapper.toEntity(product);
    ProductEntity saved = jpaRepository.save(entity);
    return ProductPersistenceMapper.toDomain(saved);
  }

  @Override
  public Optional<Product> findById(Long id) {
    return jpaRepository.findById(id).map(ProductPersistenceMapper::toDomain);
  }

  @Override
  public Optional<Product> findBySku(String sku) {
    return jpaRepository.findBySku(sku).map(ProductPersistenceMapper::toDomain);
  }

  @Override
  public List<Product> findAll() {
    return jpaRepository.findAll().stream().map(ProductPersistenceMapper::toDomain).toList();
  }

  @Override
  public boolean existsBySku(String sku) {
    return jpaRepository.existsBySku(sku);
  }

  @Override
  public void deleteById(Long id) {
    jpaRepository.deleteById(id);
  }
}
