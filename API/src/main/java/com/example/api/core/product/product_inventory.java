package com.example.api.core.product;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class product_inventory {
    private int productId;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime lastModified;
}
