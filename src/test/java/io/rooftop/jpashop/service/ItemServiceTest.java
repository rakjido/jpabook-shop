package io.rooftop.jpashop.service;

import io.rooftop.jpashop.domain.item.Album;
import io.rooftop.jpashop.domain.item.Book;
import io.rooftop.jpashop.domain.item.Item;
import io.rooftop.jpashop.exception.NotEnoughStockException;
import io.rooftop.jpashop.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;

    @Autowired ItemRepository itemRepository;

    @Test
    public void 상품입력() throws Exception {
        // Given
        Item item = new Album();
        item.setName("Wham Rap");
        item.setPrice(23000);
        item.setStockQuantity(220);

        // When
        Long saveId = itemService.saveItem(item);

        // Then
        Assertions.assertThat(item).isEqualTo(itemRepository.findOne(saveId));
    }

    @Test
    public void 상품수량추가() throws Exception {
        // Given
        Item item = new Album();
        item.setName("Wham Rap");
        item.setPrice(23000);
        item.setStockQuantity(220);
        Long saveId = itemService.saveItem(item);

        // When
        Item findItem = itemService.findOne(saveId);
        findItem.addStock(20);
        itemService.saveItem(findItem);

        // Then
        Assertions.assertThat(findItem.getStockQuantity())
                .isEqualTo(itemRepository.findOne(saveId).getStockQuantity());
        Assertions.assertThat(240)
                .isEqualTo(itemRepository.findOne(saveId).getStockQuantity());
    }

    @Test
    public void 상품재고감소() throws Exception {
        Item item = new Album();
        item.setName("Wham Rap");
        item.setPrice(23000);
        item.setStockQuantity(220);
        Long saveId = itemService.saveItem(item);

        // When
        Item findItem = itemService.findOne(saveId);
        findItem.removeStock(20);
        itemService.saveItem(findItem);

        // Then
        Assertions.assertThat(findItem.getStockQuantity())
                .isEqualTo(itemRepository.findOne(saveId).getStockQuantity());
        Assertions.assertThat(200)
                .isEqualTo(itemRepository.findOne(saveId).getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품재고감소_예외() throws Exception {
        Item item = new Album();
        item.setName("Wham Rap");
        item.setPrice(23000);
        item.setStockQuantity(220);
        Long saveId = itemService.saveItem(item);

        // When
        Item findItem = itemService.findOne(saveId);
        findItem.removeStock(300);
        itemService.saveItem(findItem);

        // Then
        fail("테스트 실패. 예외가 발생해야 한다.");
    }

}