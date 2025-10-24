package com.example.api.core.product;

import reactor.core.publisher.Mono;

/**
 * Interface réactive pour ProductService
 */
public interface ProductService {
    Mono<Product> createProduct(Product body);

    Mono<Product> getProduct(int productId);

    Mono<Void> deleteProduct(int productId);
}
