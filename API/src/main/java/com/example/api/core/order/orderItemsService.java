package com.example.api.core.order;

public interface orderItemsService {
    public order_items getItems(Long OrderId);
    public order_items pushItem(Long itemId,int quantity);
    public order_items psuhItems(order_items orderItems);
}
