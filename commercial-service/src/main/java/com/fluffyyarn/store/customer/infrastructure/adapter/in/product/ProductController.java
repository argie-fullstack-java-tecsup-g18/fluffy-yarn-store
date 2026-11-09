package com.fluffyyarn.store.customer.infrastructure.adapter.in.product;

import com.fluffyyarn.store.customer.application.port.in.product.CreateProductCommand;
import com.fluffyyarn.store.customer.application.port.in.product.UpdateProductCommand;
import com.fluffyyarn.store.customer.application.port.in.product.CreateProductUseCase;
import com.fluffyyarn.store.customer.application.port.in.product.DeleteProductUseCase;
import com.fluffyyarn.store.customer.application.port.in.product.GetProductUseCase;
import com.fluffyyarn.store.customer.application.port.in.product.ListProductUseCase;
import com.fluffyyarn.store.customer.application.port.in.product.UpdateProductUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final CreateProductUseCase createProductUseCase;
  private final GetProductUseCase getProductUseCase;
  private final ListProductUseCase listProductUseCase;
  private final UpdateProductUseCase updateProductUseCase;
  private final DeleteProductUseCase deleteProductUseCase;

  public ProductController(CreateProductUseCase createProductUseCase,
                           GetProductUseCase getProductUseCase,
                           ListProductUseCase listProductUseCase,
                           UpdateProductUseCase updateProductUseCase,
                           DeleteProductUseCase deleteProductUseCase) {
    this.createProductUseCase = createProductUseCase;
    this.getProductUseCase = getProductUseCase;
    this.listProductUseCase = listProductUseCase;
    this.updateProductUseCase = updateProductUseCase;
    this.deleteProductUseCase = deleteProductUseCase;
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductCommand command) {
    ProductResponse created = createProductUseCase.create(command);
    return ResponseEntity.created(URI.create("/products/" + created.getId())).body(created);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
    ProductResponse product = getProductUseCase.getById(id);
    return ResponseEntity.ok(product);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<List<ProductResponse>> listAll() {
    List<ProductResponse> products = listProductUseCase.listAll();
    return ResponseEntity.ok(products);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateProductCommand command) {
    ProductResponse updated = updateProductUseCase.update(id, command);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteProductUseCase.delete(id);
    return ResponseEntity.noContent().build();
  }
}
