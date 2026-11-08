package com.fluffyyarn.store.customer.infrastructure.adapter.out.product;

import com.fluffyyarn.store.customer.domain.model.Product;
import com.fluffyyarn.store.customer.infrastructure.entities.ProductEntity;

public class ProductPersistenceMapper {

  public static ProductEntity toEntity(Product product) {
    ProductEntity entity = new ProductEntity();
    entity.setId(product.getId());
    entity.setSku(product.getSku());
    entity.setEan(product.getEan());
    entity.setName(product.getName());
    entity.setDescription(product.getDescription());
    entity.setTechnicalSheet(product.getTechnicalSheet());
    entity.setPrice(product.getPrice());
    entity.setStock(product.getStock());
    return entity;
  }

  public static Product toDomain(ProductEntity entity) {
    return Product.builder()
        .id(entity.getId())
        .sku(entity.getSku())
        .ean(entity.getEan())
        .name(entity.getName())
        .description(entity.getDescription())
        .technicalSheet(entity.getTechnicalSheet())
        .price(entity.getPrice())
        .stock(entity.getStock())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }
}
