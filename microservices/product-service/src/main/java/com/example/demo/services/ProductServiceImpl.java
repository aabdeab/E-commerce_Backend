package com.example.demo.services;

import com.example.api.core.product.Product;
import com.example.api.core.product.ProductService;
import com.example.demo.ServiceUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ServiceUtil serviceUtil;
    private final List<Product> products = new ArrayList<>();

    public ProductServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
        products.add(new Product(1, "Laptop", 1200, serviceUtil.retrieveServiceAddress()));
        products.add(new Product(2, "Smartphone", 800, serviceUtil.retrieveServiceAddress()));
        products.add(new Product(3, "Tablet", 500, serviceUtil.retrieveServiceAddress()));
    }

    @Override
    public Product getProduct(int productId) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getProductId() == productId)
                .findFirst();

        return product.orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
    }

    public List<Product> getAllProducts() {
        return products;
    }
}
