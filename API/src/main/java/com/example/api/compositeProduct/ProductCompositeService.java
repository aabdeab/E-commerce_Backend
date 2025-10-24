package com.example.api.compositeProduct;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

public interface ProductCompositeService {
    @PostMapping(
            value = "/product-composite",
            consumes = "application/json")
    Mono<Void> createProduct(@RequestBody ProductAggregate body);

    @GetMapping(
            value = "/product-composite/{productId}",
            produces = "application/json")
    Mono<ProductAggregate> getProduct(@PathVariable int productId);

    @DeleteMapping(value = "/product-composite/{productId}")
    Mono<Void> deleteProduct(@PathVariable int productId);
}
