package com.example.demo.services;

import com.example.api.compositeProduct.*;
import com.example.api.core.product.Product;
import com.example.api.core.recommendation.Recommendation;
import com.example.api.core.review.Review;
import com.example.demo.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class ProductCompositeServiceImpl implements ProductCompositeService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeServiceImpl.class);

    private final ProductCompositeIntegration integration;
    private final ServiceUtil serviceUtil;

    public ProductCompositeServiceImpl(ProductCompositeIntegration integration, ServiceUtil serviceUtil) {
        this.integration = integration;
        this.serviceUtil = serviceUtil;
    }

    @Override
    @PostMapping(
            value = "/product-composite",
            consumes = "application/json")
    public Mono<Void> createProduct(@RequestBody ProductAggregate body) {
        try {
            LOG.debug("Creating new product aggregate for productId: {}", body.getProductId());

            Product product = new Product(body.getProductId(), body.getName(), body.getWeight(), null);
            integration.createProduct(product).block();

            if (body.getRecommendations() != null) {
                body.getRecommendations().forEach(r -> {
                    Recommendation recommendation = new Recommendation(
                            body.getProductId(),
                            r.getRecommendationId(),
                            r.getAuthor(),
                            r.getRate(),
                            r.getContent(),
                            null,
                            null);
                    integration.createRecommendation(recommendation).block();
                });
            }

            if (body.getReviews() != null) {
                body.getReviews().forEach(r -> {
                    Review review = new Review(
                            body.getProductId(),
                            r.getReviewId(),
                            r.getAuthor(),
                            r.getSubject(),
                            r.getContent(),
                            null);
                    integration.createReview(review).block();
                });
            }

            LOG.debug("Product aggregate created successfully for productId: {}", body.getProductId());
            return Mono.empty();

        } catch (RuntimeException ex) {
            LOG.warn("Failed to create product aggregate", ex);
            throw ex;
        }
    }

    @Override
    @GetMapping(
            value = "/product-composite/{productId}",
            produces = "application/json")
    public Mono<ProductAggregate> getProduct(@PathVariable int productId) {
        LOG.debug("Getting product composite for productId: {}", productId);

        return integration.getProduct(productId)
                .flatMap(product -> {
                    if (product == null) {
                        return Mono.error(new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product not found for id: " + productId
                        ));
                    }
                    return Mono.zip(
                            values -> {
                                List<RecommendationSummary> recommendationSummaries =
                                        ((List<Recommendation>) values[0])
                                                .stream()
                                                .map(rec -> new RecommendationSummary(
                                                        rec.getRecommendationId(),
                                                        rec.getAuthor(),
                                                        rec.getRate(),
                                                        rec.getContent()))
                                                .collect(Collectors.toList());

                                List<ReviewSummary> reviewSummaries =
                                        ((List<Review>) values[1])
                                                .stream()
                                                .map(review -> new ReviewSummary(
                                                        review.getReviewId(),
                                                        review.getAuthor(),
                                                        review.getSubject(),
                                                        review.getContent()))
                                                .collect(Collectors.toList());

                                return ProductAggregate.builder()
                                        .productId(productId)
                                        .weight(product.getWeight())
                                        .name(product.getName())
                                        .recommendations(recommendationSummaries)
                                        .reviews(reviewSummaries)
                                        .build();
                            },
                            integration.getRecommendations(productId).collectList(),
                            integration.getReviews(productId).collectList()
                    );
                });
    }

    @Override
    @DeleteMapping(value = "/product-composite/{productId}")
    public Mono<Void> deleteProduct(@PathVariable int productId) {
        try {
            LOG.debug("Deleting product aggregate for productId: {}", productId);

            integration.deleteProduct(productId).block();
            integration.deleteRecommendations(productId).block();
            integration.deleteReviews(productId).block();

            LOG.debug("Product aggregate deleted successfully for productId: {}", productId);
            return Mono.empty();

        } catch (RuntimeException ex) {
            LOG.warn("Failed to delete product aggregate", ex);
            throw ex;
        }
    }
}