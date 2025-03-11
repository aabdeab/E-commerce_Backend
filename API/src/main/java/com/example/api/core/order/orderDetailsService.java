package com.example.api.core.order;

public interface orderDetailsService {

    public order_details getDetails(long orderId);
    public order_details pushDetails(order_details details);
    public void update_details(order_details details,long orderId);
}
