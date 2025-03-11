package com.example.api.core.order;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class order_details {
    private Long itemId;
    private Long orderId;
    private Long userId;
    private BigDecimal total;
    private Long paymentId;
    private Date created_at;
    private Date modified_at;
    private Date Deleted_at;

}
