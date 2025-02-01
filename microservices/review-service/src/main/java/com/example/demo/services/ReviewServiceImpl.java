package com.example.demo.services;

import com.example.api.Exceptions.NotFoundException;
import com.example.api.core.review.Review;
import com.example.api.core.review.ReviewService;
import com.example.demo.ServiceUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ServiceUtil serviceUtil;
    private final List<Review> reviews = new ArrayList<>();

    public ReviewServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
        reviews.add(new Review(1, 1, "Alice", "Great product!", "I loved it!", serviceUtil.retrieveServiceAddress()));
        reviews.add(new Review(1, 2, "Bob", "Good", "Pretty decent overall.", serviceUtil.retrieveServiceAddress()));
        reviews.add(new Review(2, 3, "Charlie", "Not bad", "Could be improved.", serviceUtil.retrieveServiceAddress()));
    }

    @Override
    public List<Review> getReviews(int productId) {
        List<Review> productReviews = reviews.stream()
                .filter(r -> r.getProductId() == productId)
                .collect(Collectors.toList());

        if (productReviews.isEmpty()) {
            throw new NotFoundException("No reviews found for product ID: " + productId);
        }

        return productReviews;
    }

    public List<Review> getAllReviews() {
        return reviews;
    }
}
