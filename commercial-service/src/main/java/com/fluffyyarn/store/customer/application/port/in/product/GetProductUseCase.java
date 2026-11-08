package com.fluffyyarn.store.customer.application.port.in.product;

import com.fluffyyarn.store.customer.infrastructure.adapter.in.product.ProductResponse;

public interface GetProductUseCase {

  ProductResponse getById(Long id);

  ProductResponse getBySku(String sku);
}
