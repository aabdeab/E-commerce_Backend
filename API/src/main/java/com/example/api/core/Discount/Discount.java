package com.example.api.core.Discount;



import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Discount {
    private int id;
    private String name;
    private String desc;
    private BigDecimal discount_percent;
    private boolean active;
    private String created_at;
    private String modified_at;
    private String deleted_at;
}
