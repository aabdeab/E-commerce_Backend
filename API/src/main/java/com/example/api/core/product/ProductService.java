package com.example.api.core.product;

import reactor.core.publisher.Mono;

/**
 * Interface réactive pour ProductService
 */
public interface ProductService {
    Mono<Product> getProduct(int productId);
}
