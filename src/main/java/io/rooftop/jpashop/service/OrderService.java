package io.rooftop.jpashop.service;

import io.rooftop.jpashop.domain.*;
import io.rooftop.jpashop.domain.item.Item;
import io.rooftop.jpashop.repository.ItemRepository;
import io.rooftop.jpashop.repository.MemberRepository;
import io.rooftop.jpashop.repository.OrderRepository;
import io.rooftop.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     * @return
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int cout) {
        // 엔티티 조회
        Member findMember = memberRepository.findOne(memberId);
        Item findItem = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(findMember.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(findItem, findItem.getPrice(), cout);

        // 주문 생성
        Order order = Order.createOrder(findMember, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order findOrder = orderRepository.findOne(orderId);
        findOrder.cancel();
    }

    /**
     * 검색
     */

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
//        return orderRepository.findAllByString(orderSearch);
    }

}
