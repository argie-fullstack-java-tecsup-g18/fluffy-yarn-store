package com.fluffyyarn.store.customer.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter // Lombok - Genera los getters de todos los atributos.
@Setter // Lombok - Genera los setters de todos los atributos.
@NoArgsConstructor // Lombok - Genera el constructor vacío (sin parámetros).
@AllArgsConstructor // Lombok - Genera el constructor con todos los atributos.
@Builder
public class Customer {
  private Integer id;
  private Long userId; // Ya no se usa LA FK (rompe el acoplamiento con bounded context (contexto delimitado) de auth)
  private String name;
  private String lastname;
  private String documentNumber;
  private String email;
  private String address;
  private String ruc;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
