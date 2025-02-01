package com.example.demo.services;

import com.example.api.Exceptions.NotFoundException;
import com.example.api.core.recommendation.Recommendation;
import com.example.api.core.recommendation.RecommendationService;
import com.example.demo.ServiceUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class RecommendationServiceImpl implements RecommendationService {
    private final ServiceUtil serviceUtil;
    private final List<Recommendation> recommendations = new ArrayList<>();
    public RecommendationServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil=serviceUtil;
        recommendations.add(new Recommendation(1, 1, "Alice", 5, "Excellent product!", LocalDateTime.now(), serviceUtil.retrieveServiceAddress()));
        recommendations.add(new Recommendation(1, 2, "Bob", 4, "Very good product.", LocalDateTime.now(), serviceUtil.retrieveServiceAddress()));
        recommendations.add(new Recommendation(2, 3, "Charlie", 3, "Good, but could be better.", LocalDateTime.now(), serviceUtil.retrieveServiceAddress()));
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {
        List<Recommendation> productRecommendations = recommendations.stream()
                .filter(r -> r.getProductId() == productId)
                .collect(Collectors.toList());
        if (productRecommendations.isEmpty()) {
            throw new NotFoundException("No recommendations found for product ID: " + productId);
        }

        return productRecommendations;
    }

    public List<Recommendation> getRecommendations(){
        return recommendations;

    }
}

