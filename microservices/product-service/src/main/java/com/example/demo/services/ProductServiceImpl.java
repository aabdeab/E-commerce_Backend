package com.example.demo.services;

import com.example.api.core.product.Product;
import com.example.api.core.product.ProductService;
import com.example.demo.ServiceUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ServiceUtil serviceUtil;
    private final List<Product> products;

    public ProductServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
        this.products = List.of(
                new Product(1, "Laptop", 1200, serviceUtil.retrieveServiceAddress()),
                new Product(2, "Smartphone", 800, serviceUtil.retrieveServiceAddress()),
                new Product(3, "Tablet", 500, serviceUtil.retrieveServiceAddress())
        );
    }
    @Override
    public Mono<Product> getProduct(int productId) {
        return Flux.fromIterable(products)
                .filter(p -> p.getProductId() == productId)
                .next()
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found with ID: " + productId)));
    }

    public Flux<Product> getAllProducts() {
        return Flux.fromIterable(products);
    }
}
