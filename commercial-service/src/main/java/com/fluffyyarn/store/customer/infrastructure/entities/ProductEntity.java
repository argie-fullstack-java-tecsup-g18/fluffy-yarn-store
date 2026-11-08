package com.fluffyyarn.store.customer.infrastructure.entities;

import com.fluffyyarn.store.customer.domain.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

//Este es el que se comunica con la DB. Aquí sí entra Spring Boot.
@Entity(name = "Product")
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "El SKU es obligatorio")
  @Column(name = "sku", nullable = false, length = 100, unique = true)
  private String sku;

  @Column(name = "ean", length = 13, unique = true)
  private String ean;

  @NotBlank(message = "El nombre es obligatorio")
  @Column(name = "name", nullable = false, length = 200)
  private String name;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "technical_sheet", columnDefinition = "TEXT")
  private String technicalSheet;

  @NotNull(message = "El precio es obligatorio")
  @Column(name = "price", nullable = false)
  private Double price;

  @NotNull(message = "El stock es obligatorio")
  @PositiveOrZero(message = "El stock no puede ser negativo")
  @Column(name = "stock", nullable = false)
  private Integer stock;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public ProductEntity(String sku, String ean, String name, String description,
      String technicalSheet, Double price, Integer stock) {
    this.sku = sku;
    this.ean = ean;
    this.name = name;
    this.description = description;
    this.technicalSheet = technicalSheet;
    this.price = price;
    this.stock = stock;
  }

  public Product toDomain() {
    return Product.builder()
        .id(id)
        .sku(sku)
        .ean(ean)
        .name(name)
        .description(description)
        .technicalSheet(technicalSheet)
        .price(price)
        .stock(stock)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();
  }

  public static ProductEntity fromDomain(Product product) {
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
}
