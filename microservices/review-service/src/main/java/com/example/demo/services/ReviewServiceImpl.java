package com.example.demo.services;

import com.example.api.Exceptions.InvalidInputException;
import com.example.api.Exceptions.NotFoundException;
import com.example.api.core.review.Review;
import com.example.api.core.review.ReviewService;
import com.example.demo.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ServiceUtil serviceUtil;
    private final ConcurrentHashMap<Integer, Review> reviews = new ConcurrentHashMap<>();

    public ReviewServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
        // Données initiales
        Review r1 = new Review(1, 1, "Alice", "Great product!", "I loved it!", serviceUtil.retrieveServiceAddress());
        Review r2 = new Review(1, 2, "Bob", "Good", "Pretty decent overall.", serviceUtil.retrieveServiceAddress());
        Review r3 = new Review(2, 3, "Charlie", "Not bad", "Could be improved.", serviceUtil.retrieveServiceAddress());

        reviews.put(generateKey(r1.getProductId(), r1.getReviewId()), r1);
        reviews.put(generateKey(r2.getProductId(), r2.getReviewId()), r2);
        reviews.put(generateKey(r3.getProductId(), r3.getReviewId()), r3);
    }

    private int generateKey(int productId, int reviewId) {
        return productId * 10000 + reviewId;
    }

    @Override
    public Mono<Review> createReview(Review body) {
        if (body.getProductId() < 1) {
            throw new InvalidInputException("Invalid productId: " + body.getProductId());
        }

        int key = generateKey(body.getProductId(), body.getReviewId());
        if (reviews.containsKey(key)) {
            throw new InvalidInputException("Duplicate key, Product Id: " + body.getProductId() +
                    ", Review Id: " + body.getReviewId());
        }

        Review review = new Review(
            body.getProductId(),
            body.getReviewId(),
            body.getAuthor(),
            body.getSubject(),
            body.getContent(),
            serviceUtil.retrieveServiceAddress()
        );

        reviews.put(key, review);
        LOG.info("Review created: productId={}, reviewId={}", review.getProductId(), review.getReviewId());

        return Mono.just(review);
    }

    @Override
    public Flux<Review> getReviews(int productId) {
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        LOG.info("Will get reviews for product with id={}", productId);

        List<Review> productReviews = reviews.values().stream()
                .filter(r -> r.getProductId() == productId)
                .collect(Collectors.toList());

        return Flux.fromIterable(productReviews);
    }

    @Override
    public Mono<Void> deleteReviews(int productId) {
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        LOG.debug("deleteReviews: tries to delete reviews for the product with productId: {}", productId);

        List<Integer> keysToDelete = reviews.entrySet().stream()
                .filter(entry -> entry.getValue().getProductId() == productId)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());

        keysToDelete.forEach(reviews::remove);

        return Mono.empty();
    }

    public List<Review> getAllReviews() {
        return new ArrayList<>(reviews.values());
    }
}
