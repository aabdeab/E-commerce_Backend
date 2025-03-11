package com.example.demo.services;

import com.example.api.compositeProduct.*;
import com.example.api.core.product.Product;
import com.example.demo.ServiceUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductCompositeServiceImpl implements ProductCompositeService {
    private final ProductCompositeIntegration integration;
    private final ServiceUtil serviceUtil;

    public ProductCompositeServiceImpl(ProductCompositeIntegration integration, ServiceUtil serviceUtil) {
        this.integration = integration;
        this.serviceUtil = serviceUtil;
    }
    @Override
    @GetMapping(
            value = "/product-composite/{productId}",
            produces = "application/json")
    public ProductAggregate getProduct(@PathVariable int productId) {
        Product product = integration.getProduct(productId);

        if (product == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Product not found for id: " + productId
            );
        }

        List<RecommendationSummary> recommendationSummaries =
                integration.getRecommendations(productId) != null ?
                        integration.getRecommendations(productId)
                                .stream()
                                .map(rec -> new RecommendationSummary(
                                        rec.getRecommendationId(),
                                        rec.getAuthor(),
                                        rec.getRate()))
                                .collect(Collectors.toList()) :
                        Collections.emptyList();

        List<ReviewSummary> reviewSummaries =
                integration.getReviews(productId) != null ?
                        integration.getReviews(productId)
                                .stream()
                                .map(review -> new ReviewSummary(
                                        review.getReviewId(),
                                        review.getAuthor(),
                                        review.getSubject()))
                                .collect(Collectors.toList()) :
                        Collections.emptyList();
        ///
        ServiceAddresses serviceAddresses = ServiceAddresses.builder()
                .productAddress(product.)
                .recommendationAddress(rev)

        return ProductAggregate.builder()
                .productId(productId)
                .weight(product.getWeight())
                .name(product.getName())
                .recommendations(recommendationSummaries)
                .reviews(reviewSummaries)
                .serviceAddresses()
                .build();
    }

}