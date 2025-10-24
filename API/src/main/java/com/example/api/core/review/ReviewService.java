package com.example.api.core.review;

import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReviewService {
    Mono<Review> createReview(Review body);

    @GetMapping(value="/review",produces = "application/json")
    Flux<Review> getReviews(int productId);

    Mono<Void> deleteReviews(int productId);
}
