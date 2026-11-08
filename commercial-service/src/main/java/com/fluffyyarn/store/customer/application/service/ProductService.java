package com.fluffyyarn.store.customer.application.service;

import com.fluffyyarn.store.customer.application.port.in.product.CreateProductCommand;
import com.fluffyyarn.store.customer.application.port.in.product.UpdateProductCommand;
import com.fluffyyarn.store.customer.infrastructure.adapter.in.product.ProductResponse;
import com.fluffyyarn.store.customer.application.port.in.product.CreateProductUseCase;
import com.fluffyyarn.store.customer.application.port.in.product.DeleteProductUseCase;
import com.fluffyyarn.store.customer.application.port.in.product.GetProductUseCase;
import com.fluffyyarn.store.customer.application.port.in.product.ListProductUseCase;
import com.fluffyyarn.store.customer.application.port.in.product.UpdateProductUseCase;
import com.fluffyyarn.store.customer.application.port.out.product.ProductRepositoryPort;
import com.fluffyyarn.store.customer.domain.exception.DuplicateProductException;
import com.fluffyyarn.store.customer.domain.exception.ProductNotFoundException;
import com.fluffyyarn.store.customer.domain.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService implements CreateProductUseCase, UpdateProductUseCase, GetProductUseCase, ListProductUseCase, DeleteProductUseCase {

  private final ProductRepositoryPort repository;

  public ProductService(ProductRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public ProductResponse create(CreateProductCommand command) {
    if (repository.existsBySku(command.getSku())) {
      throw new DuplicateProductException("Ya existe un producto con SKU " + command.getSku());
    }

    Product product = Product.builder()
        .sku(command.getSku())
        .ean(command.getEan())
        .name(command.getName())
        .description(command.getDescription())
        .technicalSheet(command.getTechnicalSheet())
        .price(command.getPrice())
        .stock(command.getStock())
        .build();

    Product saved = repository.save(product);
    return toResponse(saved);
  }

  @Override
  @Transactional
  public ProductResponse update(Long id, UpdateProductCommand command) {
    Product product = repository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException("Producto con id " + id + " no encontrado."));

    product.setName(command.getName());
    product.setDescription(command.getDescription());
    product.setTechnicalSheet(command.getTechnicalSheet());
    product.setPrice(command.getPrice());
    product.setStock(command.getStock());

    Product updated = repository.save(product);
    return toResponse(updated);
  }

  @Override
  @Transactional(readOnly = true)
  public ProductResponse getById(Long id) {
    Product product = repository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException("Producto con id " + id + " no encontrado."));
    return toResponse(product);
  }

  @Override
  @Transactional(readOnly = true)
  public ProductResponse getBySku(String sku) {
    Product product = repository.findBySku(sku)
        .orElseThrow(() -> new ProductNotFoundException("Producto con SKU " + sku + " no encontrado."));
    return toResponse(product);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProductResponse> listAll() {
    List<Product> products = repository.findAll();
    return products.stream().map(this::toResponse).toList();
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (!repository.findById(id).isPresent()) {
      throw new ProductNotFoundException("Producto con id " + id + " no encontrado.");
    }
    repository.deleteById(id);
  }

  private ProductResponse toResponse(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getSku(),
        product.getEan(),
        product.getName(),
        product.getDescription(),
        product.getTechnicalSheet(),
        product.getPrice(),
        product.getStock(),
        product.getCreatedAt(),
        product.getUpdatedAt()
    );
  }
}
