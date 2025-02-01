package com.example.demo.services;

import com.example.api.core.product.Product;
import com.example.api.core.recommendation.Recommendation;
import com.example.api.core.review.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service

public class ProductCompositeIntegration {
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    public ProductCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,
            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") int productServicePort,
            @Value("${app.recommendation-service.host}") String recommendationServiceHost,
            @Value("${app.recommendation-service.port}") int recommendationServicePort,
            @Value("${app.review-service.host}") String reviewServiceHost,
            @Value("${app.review-service.port}") int reviewServicePort
    ) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        this.productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/products/";
        this.recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort + "/recommendations?productID=";
        this.reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/reviews?productID=";
    }

    public Product getProduct(int productId) {
        String url = productServiceUrl + productId;
        return restTemplate.getForObject(url, Product.class);
    }

    public List<Recommendation> getRecommendations(int productId) {
        String url = recommendationServiceUrl + productId;
        try {
            return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Recommendation>>() {}).getBody();
        } catch (HttpClientErrorException ex) {
            return handleHttpClientErrorException(ex);
        }
    }

    public List<Review> getReviews(int productId) {
        String url = reviewServiceUrl + productId;
        try {
            return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {}).getBody();
        } catch (HttpClientErrorException ex) {
            return handleHttpClientErrorException(ex);
        }
    }

    private <T> T handleHttpClientErrorException(HttpClientErrorException ex) {
        return null; // À améliorer avec des exceptions spécifiques
    }
}
