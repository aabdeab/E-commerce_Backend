package com.example.demo.controller;


import com.example.api.core.recommendation.Recommendation;
import com.example.demo.services.RecommendationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RecommendationController {
    private final RecommendationServiceImpl RSI;
    @GetMapping("/recommendations")
    public List<Recommendation> getRecommendations(
            @RequestParam(value = "productID", required = false) Integer productID){
        if(productID==null){
            return RSI.getRecommendations();
        }
        return RSI.getRecommendations(productID);
    }
}
