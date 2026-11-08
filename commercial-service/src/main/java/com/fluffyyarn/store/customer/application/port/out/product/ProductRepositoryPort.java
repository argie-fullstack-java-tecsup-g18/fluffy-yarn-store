package com.fluffyyarn.store.customer.application.port.out.product;

import com.fluffyyarn.store.customer.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {

  Product save(Product product);

  Optional<Product> findById(Long id);

  Optional<Product> findBySku(String sku);

  List<Product> findAll();

  boolean existsBySku(String sku);

  void deleteById(Long id);
}
