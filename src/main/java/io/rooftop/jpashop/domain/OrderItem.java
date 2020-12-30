package io.rooftop.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.rooftop.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;     // 주문가격
    private int count;          // 주문수량

    // ==생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) { // 할인가격, 수량보너스 가능
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    // == 비즈니스 로직 == //
    public void cancel() {
        this.getItem().addStock(this.count);
    }

    // == 조회 로직 == //

    public int getTotalPrice() {
        return getOrderPrice()*getCount();
    }
}

