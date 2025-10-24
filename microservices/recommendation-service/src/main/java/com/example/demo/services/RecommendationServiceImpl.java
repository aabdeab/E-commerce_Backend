package com.example.demo.services;

import com.example.api.Exceptions.InvalidInputException;
import com.example.api.Exceptions.NotFoundException;
import com.example.api.core.recommendation.Recommendation;
import com.example.api.core.recommendation.RecommendationService;
import com.example.demo.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    private static final Logger LOG = LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final ServiceUtil serviceUtil;
    private final ConcurrentHashMap<Integer, Recommendation> recommendations = new ConcurrentHashMap<>();
    private int nextId = 1;

    public RecommendationServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
        // Données initiales
        Recommendation r1 = new Recommendation(1, 1, "Alice", 5, "Excellent product!", LocalDateTime.now(), serviceUtil.retrieveServiceAddress());
        Recommendation r2 = new Recommendation(1, 2, "Bob", 4, "Very good product.", LocalDateTime.now(), serviceUtil.retrieveServiceAddress());
        Recommendation r3 = new Recommendation(2, 3, "Charlie", 3, "Good, but could be better.", LocalDateTime.now(), serviceUtil.retrieveServiceAddress());

        recommendations.put(generateKey(r1.getProductId(), r1.getRecommendationId()), r1);
        recommendations.put(generateKey(r2.getProductId(), r2.getRecommendationId()), r2);
        recommendations.put(generateKey(r3.getProductId(), r3.getRecommendationId()), r3);
        nextId = 4;
    }

    private int generateKey(int productId, int recommendationId) {
        return productId * 10000 + recommendationId;
    }

    @Override
    public Mono<Recommendation> createRecommendation(Recommendation body) {
        if (body.getProductId() < 1) {
            throw new InvalidInputException("Invalid productId: " + body.getProductId());
        }

        int key = generateKey(body.getProductId(), body.getRecommendationId());
        if (recommendations.containsKey(key)) {
            throw new InvalidInputException("Duplicate key, Product Id: " + body.getProductId() +
                    ", Recommendation Id: " + body.getRecommendationId());
        }

        Recommendation recommendation = new Recommendation(
            body.getProductId(),
            body.getRecommendationId(),
            body.getAuthor(),
            body.getRate(),
            body.getContent(),
            LocalDateTime.now(),
            serviceUtil.retrieveServiceAddress()
        );

        recommendations.put(key, recommendation);
        LOG.info("Recommendation created: productId={}, recommendationId={}",
                recommendation.getProductId(), recommendation.getRecommendationId());

        return Mono.just(recommendation);
    }

    @Override
    public Flux<Recommendation> getRecommendations(int productId) {
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        LOG.info("Will get recommendations for product with id={}", productId);

        List<Recommendation> productRecommendations = recommendations.values().stream()
                .filter(r -> r.getProductId() == productId)
                .collect(Collectors.toList());

        return Flux.fromIterable(productRecommendations);
    }

    @Override
    public Mono<Void> deleteRecommendations(int productId) {
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        LOG.debug("deleteRecommendations: tries to delete recommendations for the product with productId: {}", productId);

        List<Integer> keysToDelete = recommendations.entrySet().stream()
                .filter(entry -> entry.getValue().getProductId() == productId)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());

        keysToDelete.forEach(recommendations::remove);

        return Mono.empty();
    }

    public List<Recommendation> getAllRecommendations() {
        return new ArrayList<>(recommendations.values());
    }
}
