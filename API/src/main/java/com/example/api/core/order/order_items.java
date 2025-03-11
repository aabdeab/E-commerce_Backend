package com.example.api.core.order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor

public class order_items {
    private long itemId;
    private long orderId;
    private long productId;
    private int quantity;
    private Date created_At;
    private Date modified_At;
    private Date deleted_At;
}
