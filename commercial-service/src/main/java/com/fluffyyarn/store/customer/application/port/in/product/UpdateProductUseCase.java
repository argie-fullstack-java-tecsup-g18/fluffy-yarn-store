package com.fluffyyarn.store.customer.application.port.in.product;

import com.fluffyyarn.store.customer.application.port.in.product.UpdateProductCommand;
import com.fluffyyarn.store.customer.infrastructure.adapter.in.product.ProductResponse;

public interface UpdateProductUseCase {

  ProductResponse update(Long id, UpdateProductCommand command);
}
