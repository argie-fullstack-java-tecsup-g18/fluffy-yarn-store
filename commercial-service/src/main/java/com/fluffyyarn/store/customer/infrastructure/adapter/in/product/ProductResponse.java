package com.fluffyyarn.store.customer.infrastructure.adapter.in.product;

import java.time.LocalDateTime;

public class ProductResponse {

  private Long id;
  private String sku;
  private String ean;
  private String name;
  private String description;
  private String technicalSheet;
  private Double price;
  private Integer stock;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public ProductResponse() {
  }

  public ProductResponse(Long id, String sku, String ean, String name, String description,
      String technicalSheet, Double price, Integer stock, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.sku = sku;
    this.ean = ean;
    this.name = name;
    this.description = description;
    this.technicalSheet = technicalSheet;
    this.price = price;
    this.stock = stock;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getEan() {
    return ean;
  }

  public void setEan(String ean) {
    this.ean = ean;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTechnicalSheet() {
    return technicalSheet;
  }

  public void setTechnicalSheet(String technicalSheet) {
    this.technicalSheet = technicalSheet;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
