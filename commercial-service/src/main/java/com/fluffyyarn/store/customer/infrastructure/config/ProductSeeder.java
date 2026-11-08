package com.fluffyyarn.store.customer.infrastructure.config;

import com.fluffyyarn.store.customer.application.port.out.product.ProductRepositoryPort;
import com.fluffyyarn.store.customer.domain.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Crea productos iniciales para que la tienda tenga catalogo desde el primer arranque.
//
// Va en un CommandLineRunner para poder usar el ProductRepositoryPort y los dominio
// directamente, sin depender de un INSERT en SQL con datos hardcodeados.
//
// Idempotente: si ya existen productos en la base, no hace nada. Este es el caso
// normal en cada reinicio.
@Component
public class ProductSeeder implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(ProductSeeder.class);

  private final ProductRepositoryPort productRepository;

  public ProductSeeder(ProductRepositoryPort productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void run(String... args) {
    if (!productRepository.findAll().isEmpty()) {
      log.info("Ya existen productos en la base, no se insertan productos iniciales.");
      return;
    }

    productRepository.save(Product.builder()
        .sku("PROD-001")
        .ean("1234567890123")
        .name("Camiseta de algodón orgánico")
        .description("Camiseta básica 100% algodón orgánico, corte clásico.")
        .technicalSheet("Tela: 100% algodón orgánico. Gramaje: 180 g/m2.")
        .price(19.99)
        .stock(50)
        .build());

    productRepository.save(Product.builder()
        .sku("PROD-002")
        .ean("1234567890124")
        .name("Pantalón vaquero slim fit")
        .description("Pantalón vaquero azul, ajuste slim fit.")
        .technicalSheet("Tela: 98% algodón, 2% elastano.")
        .price(49.99)
        .stock(30)
        .build());

    productRepository.save(Product.builder()
        .sku("PROD-003")
        .ean("1234567890125")
        .name("Zapatillas deportivas running")
        .description("Zapatillas para running, amortiguación ligera.")
        .technicalSheet("Suela: goma EVA. Upper: malla transpirable.")
        .price(79.99)
        .stock(20)
        .build());

    log.info("Productos iniciales creados en la base de datos.");
  }
}
