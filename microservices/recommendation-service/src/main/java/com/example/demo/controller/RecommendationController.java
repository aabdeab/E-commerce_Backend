package com.example.demo.controller;


import com.example.api.core.recommendation.Recommendation;
import com.example.demo.services.RecommendationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationServiceImpl RSI;

    @GetMapping
    public Flux<Recommendation> getRecommendations(
            @RequestParam(value = "productId", required = false) Integer productId){
        if(productId == null){
            return Flux.fromIterable(RSI.getAllRecommendations());
        }
        return RSI.getRecommendations(productId);
    }
}
