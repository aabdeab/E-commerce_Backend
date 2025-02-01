package com.example.api.core.review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
public class Review {
    private final int productId;
    private final int reviewId;
    private final String author;
    private final String subject;
    private final String content;
    private final String serviceAddress;

    public Review() {
        productId=0;
        author=null;
        reviewId=0;
        subject=null;
        content=null;
        serviceAddress=null;

    }
}
