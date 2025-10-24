package com.example.demo.services;

import com.example.api.core.recommendation.Recommendation;
import com.example.api.core.recommendation.RecommendationService;
import com.example.api.event.Event;
import com.example.api.Exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MessageProcessorConfig {

    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessorConfig.class);

    private final RecommendationService recommendationService;

    @Autowired
    public MessageProcessorConfig(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Bean
    public Consumer<Event<Integer, Recommendation>> messageProcessor() {
        return event -> {
            LOG.info("Processing message created at {}...", event.getEventCreatedAt());

            switch (event.getEventType()) {
                case CREATE:
                    Recommendation recommendation = event.getData();
                    LOG.info("Create recommendation with Product ID: {} / Recommendation ID: {}",
                            recommendation.getProductId(), recommendation.getRecommendationId());
                    recommendationService.createRecommendation(recommendation).block();
                    break;

                case DELETE:
                    int productId = event.getKey();
                    LOG.info("Delete recommendations with Product ID: {}", productId);
                    recommendationService.deleteRecommendations(productId).block();
                    break;

                default:
                    String errorMessage = "Incorrect event type: " + event.getEventType() + ", expected CREATE or DELETE";
                    LOG.warn(errorMessage);
                    throw new InvalidInputException(errorMessage);
            }

            LOG.info("Message processing done!");
        };
    }
}
