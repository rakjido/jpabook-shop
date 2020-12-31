package io.rooftop.jpashop.api.dto;

import io.rooftop.jpashop.domain.Address;
import io.rooftop.jpashop.domain.Order;
import io.rooftop.jpashop.domain.OrderItem;
import io.rooftop.jpashop.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
//    private List<OrderItem> orderItems; // Dto에서 entity를 사용하면 절대 안된다.
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
//        order.getOrderItems().stream().forEach(o -> o.getItem().getName());
//        this.orderItems = order.getOrderItems();
        this.orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(Collectors.toList());
    }
}
