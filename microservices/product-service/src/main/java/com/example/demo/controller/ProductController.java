package com.example.demo.controller;


import com.example.api.core.product.Product;
import com.example.demo.services.ProductServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductServiceImpl productService;
    ProductController(ProductServiceImpl p){
        this.productService=p;
    }
    @GetMapping("/products/{productID}")
    public Product getProducts(@PathVariable Integer productID) {
        return productService.getProduct(productID);


    }
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
