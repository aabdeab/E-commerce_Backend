package com.example.demo.controller;


import com.example.api.core.review.Review;
import com.example.demo.services.ReviewServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
       public final ReviewServiceImpl RVS;

       @GetMapping
       public List<Review> getReviews(@RequestParam(value = "productID",required = false) Integer productID){
           if(productID==null){
               return RVS.getAllReviews();
           }
           return RVS.getAllReviews();
       }

}
