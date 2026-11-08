package com.fluffyyarn.store.customer.application.port.in.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class CreateProductCommand {

  @NotBlank(message = "El SKU es obligatorio")
  @Size(max = 100, message = "El SKU no puede exceder 100 caracteres")
  private String sku;

  @Size(max = 13, message = "El EAN no puede exceder 13 caracteres")
  private String ean;

  @NotBlank(message = "El nombre es obligatorio")
  @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
  private String name;

  private String description;

  private String technicalSheet;

  @NotNull(message = "El precio es obligatorio")
  @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
  private Double price;

  @NotNull(message = "El stock es obligatorio")
  @PositiveOrZero(message = "El stock no puede ser negativo")
  private Integer stock;

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
}
