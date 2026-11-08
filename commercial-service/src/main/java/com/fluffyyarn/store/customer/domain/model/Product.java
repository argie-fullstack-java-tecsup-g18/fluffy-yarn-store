package com.fluffyyarn.store.customer.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter // Lombok - Genera los getters de todos los atributos.
@Setter // Lombok - Genera los setters de todos los atributos.
@NoArgsConstructor // Lombok - Genera el constructor vacío (sin parámetros).
@AllArgsConstructor // Lombok - Genera el constructor con todos los atributos.
@Builder
public class Product {
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

//  private Long brandId; para implementar mas adelante
//  private Long categoryProductId;
//  private Long promoId;
//  private Integer productStatus;
}
// EAN (European Article Number) código numérico estándar de 13 dígitos que identifica un producto de forma única en t0do el mundo, representado habitualmente como un código de barras.