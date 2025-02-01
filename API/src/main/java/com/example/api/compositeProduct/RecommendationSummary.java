package com.example.api.compositeProduct;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class RecommendationSummary {
    private final int recommandationId;
    private final String author;
    private final int rate;
}
