package com.example.api.core.recommendation;

import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecommendationService {
    Mono<Recommendation> createRecommendation(Recommendation body);

    @GetMapping(
            value = "/recommendation",
            produces = "application/json")
    Flux<Recommendation> getRecommendations(int productId);

    Mono<Void> deleteRecommendations(int productId);
}
