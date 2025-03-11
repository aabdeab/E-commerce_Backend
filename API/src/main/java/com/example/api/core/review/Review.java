package com.example.api.core.review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
public class Review {
    private  int productId;
    private  int reviewId;
    private  String author;
    private  String subject;
    private  String content;
    private  String serviceAddress;

    public Review() {
        productId=0;
        author=null;
        reviewId=0;
        subject=null;
        content=null;
        serviceAddress=null;

    }
}
