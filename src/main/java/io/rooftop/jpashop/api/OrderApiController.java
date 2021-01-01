package io.rooftop.jpashop.api;

import io.rooftop.jpashop.api.dto.OrderDto;
import io.rooftop.jpashop.repository.order.query.OrderFlatDto;
import io.rooftop.jpashop.repository.order.query.OrderItemQueryDto;
import io.rooftop.jpashop.repository.order.query.OrderQueryDto;
import io.rooftop.jpashop.domain.Order;
import io.rooftop.jpashop.domain.OrderItem;
import io.rooftop.jpashop.repository.OrderRepository;
import io.rooftop.jpashop.repository.OrderSearch;
import io.rooftop.jpashop.repository.order.query.OrderQueryRepository;
import io.rooftop.jpashop.service.query.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress(); // Lazy Loading
            List<OrderItem> orderItems = order.getOrderItems(); // Lazy Loading
            orderItems.stream().forEach(o -> o.getItem().getName()); // Lazy Loading
//            for (OrderItem orderItem : orderItems) {
//                orderItem.getItem().getName();
//            }
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o)).collect(toList());
        return result;
    }

//    @GetMapping("/api/v3/orders")
//    public List<OrderDto> ordersV3() {
//        List<Order> orders = orderRepository.findAllWithItem();
//        List<OrderDto> result = orders.stream()
//                .map(o -> new OrderDto(o)).collect(toList());
//        return result;
//    }

    // spring.jpa.open-in-view : false 일 경우
    private final OrderQueryService orderQueryService;

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        return orderQueryService.orderV3();
    }


    // General Best Solution : v3.1 ===============================================

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o)).collect(toList());
        return result;

    }
    //=============================================================================

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDtoOptimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDtoFlat();
        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(),
                                o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                                o.getItemName(), o.getOrderPrice(), o.getCount()), toList())

                )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                        e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                        e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }

}