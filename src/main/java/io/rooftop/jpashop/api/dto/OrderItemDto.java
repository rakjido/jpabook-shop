package io.rooftop.jpashop.api.dto;

import io.rooftop.jpashop.domain.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemDto {

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}
