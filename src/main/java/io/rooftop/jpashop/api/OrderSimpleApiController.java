package io.rooftop.jpashop.api;

import io.rooftop.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import io.rooftop.jpashop.domain.Address;
import io.rooftop.jpashop.domain.Order;
import io.rooftop.jpashop.domain.OrderStatus;
import io.rooftop.jpashop.repository.OrderRepository;
import io.rooftop.jpashop.repository.OrderSearch;
import io.rooftop.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * xToOne (ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for(Order order : all) {
            order.getMember().getName(); // Lazy 강제 초기화 (Member를 가져온다)
            order.getDelivery().getAddress(); // Lazy 강제 초기화 (Delivery를 가져온다)
        }
        return all;
    }


    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
//  (1+N)문제
//  order조회 1 (ORDER 조회 결과 N개) + Member N개 + Delivery N개

//  5개의 Query발생
//  1) order 조회(2개)
//  2) order1에 해당하는 member 조회
//  3) order1에 해당하는 delivery 조회
//  4) order2에 해당하는 member 조회
//  5) order2에 해당하는 delivery 조회
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))  // .map(SimpleOrderDto::New)
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders=orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream().map(SimpleOrderDto::new)
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }


    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName(); // LAZY 초기화
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();  // LAZY 초기화
        }
    }

}
