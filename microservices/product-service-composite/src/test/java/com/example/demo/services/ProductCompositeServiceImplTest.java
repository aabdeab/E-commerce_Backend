package com.example.demo;

import com.example.api.compositeProduct.ProductAggregate;
import com.example.demo.services.ProductCompositeServiceImpl;
import com.example.demo.services.ProductCompositeIntegration;
import com.example.api.core.product.Product;
import com.example.api.core.recommendation.Recommendation;
import com.example.api.core.review.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductCompositeServiceImpl.class)
public class ProductCompositeServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductCompositeIntegration productCompositeIntegration;

    @MockBean
    private ServiceUtil serviceUtil;

    @Test
    public void testGetProductComposite() throws Exception {
        int productId = 1;

        Product mockProduct = Product.builder()
                .productId(1)
                .name("Laptop")
                .weight(1200)
                .serviceAddress("127.0.0.1")
                .build();

        List<Recommendation> mockRecommendations = List.of(
                Recommendation.builder()
                        .productId(1)
                        .recommendationId(5)
                        .author("Alice")
                        .rate(5)
                        .content("Great laptop!")
                        .timestamp(LocalDateTime.now())
                        .serviceAddress("127.0.0.1")
                        .build(),
                Recommendation.builder()
                        .productId(1)
                        .recommendationId(2)
                        .author("Bob")
                        .rate(4)
                        .content("Good performance.")
                        .timestamp(LocalDateTime.now())
                        .serviceAddress("127.0.0.1")
                        .build()
        );

        List<Review> mockReviews = List.of(
                Review.builder()
                        .reviewId(1)
                        .author("Alice")
                        .subject("Great product!")
                        .content("Highly recommend this laptop.")
                        .serviceAddress("127.0.0.1")
                        .build(),
                Review.builder()
                        .reviewId(2)
                        .author("Bob")
                        .subject("Good")
                        .content("Decent battery life.")
                        .serviceAddress("127.0.0.1")
                        .build(),
                Review.builder()
                        .reviewId(3)
                        .author("Charlie")
                        .subject("Not bad")
                        .content("Expected better screen quality.")
                        .serviceAddress("127.0.0.1")
                        .build()
        );

        when(productCompositeIntegration.getProduct(productId)).thenReturn(mockProduct);
        when(productCompositeIntegration.getRecommendations(productId)).thenReturn(mockRecommendations);
        when(productCompositeIntegration.getReviews(productId)).thenReturn(mockReviews);
        mockMvc.perform(get("/product-composite/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.weight").value(1200))
                .andExpect(jsonPath("$.recommendations[0].recommandationId").value(5))
                .andExpect(jsonPath("$.reviews[0].reviewId").value(1))
                .andExpect(jsonPath("$.reviews[0].subject").value("Great product!"));
    }

    @Test
    public void testGetProduct_NotFound() throws Exception {
        int productId = 1;

        when(productCompositeIntegration.getProduct(productId)).thenReturn(null);

        mockMvc.perform(get("/product-composite/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}