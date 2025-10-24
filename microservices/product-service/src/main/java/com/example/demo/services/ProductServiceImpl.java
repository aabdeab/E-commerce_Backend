package com.example.demo.services;

import com.example.api.core.product.Product;
import com.example.api.core.product.ProductService;
import com.example.api.Exceptions.InvalidInputException;
import com.example.api.Exceptions.NotFoundException;
import com.example.demo.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ServiceUtil serviceUtil;
    private final ConcurrentHashMap<Integer, Product> products = new ConcurrentHashMap<>();

    public ProductServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
        // Données initiales
        products.put(1, new Product(1, "Laptop", 1200, serviceUtil.retrieveServiceAddress()));
        products.put(2, new Product(2, "Smartphone", 800, serviceUtil.retrieveServiceAddress()));
        products.put(3, new Product(3, "Tablet", 500, serviceUtil.retrieveServiceAddress()));
    }

    @Override
    public Mono<Product> createProduct(Product body) {
        if (body.getProductId() < 1) {
            throw new InvalidInputException("Invalid productId: " + body.getProductId());
        }

        if (products.containsKey(body.getProductId())) {
            throw new InvalidInputException("Duplicate key, Product Id: " + body.getProductId());
        }

        Product product = new Product(
            body.getProductId(),
            body.getName(),
            body.getWeight(),
            serviceUtil.retrieveServiceAddress()
        );

        products.put(product.getProductId(), product);
        LOG.info("Product created: {}", product.getProductId());

        return Mono.just(product);
    }

    @Override
    public Mono<Product> getProduct(int productId) {
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        Product product = products.get(productId);
        if (product == null) {
            throw new NotFoundException("No product found for productId: " + productId);
        }

        LOG.info("Will get product info for id={}", productId);
        return Mono.just(product);
    }

    @Override
    public Mono<Void> deleteProduct(int productId) {
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        LOG.debug("deleteProduct: tries to delete an entity with productId: {}", productId);
        products.remove(productId);

        return Mono.empty();
    }

    public Flux<Product> getAllProducts() {
        return Flux.fromIterable(new ArrayList<>(products.values()));
    }
}
