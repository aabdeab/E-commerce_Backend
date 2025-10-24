package com.example.demo.controller;

import com.example.api.core.product.Product;
import com.example.demo.services.ProductServiceImpl;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public Mono<Product> getProduct(@PathVariable Integer productId) {
        return productService.getProduct(productId);
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
