package com.example.api.core.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class orderRequestDto {
    private int orderId;
    private long user_id;
    private BigDecimal total;
}
