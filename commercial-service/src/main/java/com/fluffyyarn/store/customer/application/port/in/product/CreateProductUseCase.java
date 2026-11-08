package com.fluffyyarn.store.customer.application.port.in.product;

import com.fluffyyarn.store.customer.application.port.in.product.CreateProductCommand;
import com.fluffyyarn.store.customer.infrastructure.adapter.in.product.ProductResponse;

public interface CreateProductUseCase {

  ProductResponse create(CreateProductCommand command);
}
