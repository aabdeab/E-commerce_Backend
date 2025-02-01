package com.example.demo.controller;


import com.example.api.core.product.Product;
import com.example.demo.services.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ProductServiceImpl p;

    @Test
    public void getProductsTest() throws Exception  {
        Product MockProduct=Product.builder()
                .productId(1)
                .name("laptop")
                .weight(800)
                .serviceAddress("some address")
                .build();
        when(p.getProduct(1)).thenReturn(MockProduct);
        mockMvc.perform(get("/products/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("laptop"));

    }
}
