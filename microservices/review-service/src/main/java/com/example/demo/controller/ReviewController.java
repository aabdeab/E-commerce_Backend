package com.example.demo.controller;


import com.example.api.core.review.Review;
import com.example.demo.services.ReviewServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {
       public final ReviewServiceImpl RVS;

       @GetMapping
       public Flux<Review> getReviews(@RequestParam(value = "productId", required = false) Integer productId){
           if(productId == null){
               return Flux.fromIterable(RVS.getAllReviews());
           }
           return RVS.getReviews(productId);
       }
}
