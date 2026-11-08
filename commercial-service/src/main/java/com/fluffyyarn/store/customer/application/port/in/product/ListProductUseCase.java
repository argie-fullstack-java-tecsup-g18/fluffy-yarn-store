package com.fluffyyarn.store.customer.application.port.in.product;

import com.fluffyyarn.store.customer.infrastructure.adapter.in.product.ProductResponse;

import java.util.List;

public interface ListProductUseCase {

  List<ProductResponse> listAll();
}
