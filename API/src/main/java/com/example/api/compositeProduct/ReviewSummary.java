package com.example.api.compositeProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ReviewSummary {
    private final int reviewId;
    private final String author;
    private final String subject;
}
