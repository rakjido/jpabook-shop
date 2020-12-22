package io.rooftop.jpashop.service;

import io.rooftop.jpashop.domain.Address;
import io.rooftop.jpashop.domain.Member;
import io.rooftop.jpashop.domain.Order;
import io.rooftop.jpashop.domain.OrderStatus;
import io.rooftop.jpashop.domain.item.Book;
import io.rooftop.jpashop.exception.NotEnoughStockException;
import io.rooftop.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // Given
        Member member = createMember();

        Book book = createBook("JPA BOOK", 35000, 230, "Park", "234");

        int orderCount = 2;

        // When
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // Then
        Order findOrder = orderRepository.findOne(orderId);

//        Assertions.assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, findOrder.getStatus());
        assertEquals("주문한 상품 종류수가 정확해야 한다", 1, findOrder.getOrderItems().size());
        assertEquals("주문가격은 가격*수량이다.", 35000*orderCount, findOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",228, book.getStockQuantity());

    }


    @Test
    public void 주문취소() throws Exception {
        // Given
        Member member = createMember();

        Book book = createBook("JPA BOOK", 35000, 230, "Park", "234");

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // When
        orderService.cancelOrder(orderId);

        // Then
        Order findOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL이다.",OrderStatus.CANCEL, findOrder.getStatus());
        assertEquals("재고가 주문취소 전과 동일해야 한다.", 230, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        // Given
        Member member = createMember();
        Book book = createBook("JPA Book", 10000, 10, "Park", "234");

        int orderCount = 11;

        // When
        orderService.order(member.getId(), book.getId(), orderCount);

        // Then
        fail("실패: 재고 수량 부족 예외가 발생해야 한다.");
    }

    private Book createBook(String name, int price, int quantity, String author, String isbn) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("Hideo");
        member.setAddress(new Address("Seoul", "Samchung", "234234"));
        em.persist(member);
        return member;
    }

}
