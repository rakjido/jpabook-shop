package io.rooftop.jpashop.web;

import io.rooftop.jpashop.domain.item.Book;
import io.rooftop.jpashop.domain.item.Item;
import io.rooftop.jpashop.service.ItemService;
import io.rooftop.jpashop.web.dto.BookFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        log.info(getClass().getName() + " / " + methodName);

        model.addAttribute("form", new BookFormDto());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String crate(BookFormDto form) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        log.info(getClass().getName() + " / " + methodName);

        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());

        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);

        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        log.info(getClass().getName() + " / " + methodName);

        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateForm(@PathVariable("itemId") Long itemId, Model model) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        log.info(getClass().getName() + " / " + methodName);

        Book item = (Book) itemService.findOne(itemId);

        BookFormDto form = new BookFormDto();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());

        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookFormDto form) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        log.info(getClass().getName() + " / " + methodName);

//        Book book = new Book();
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
//
//        // 가능하면 merge 사용을 하지 말 것
//        itemService.saveItem(book);

        itemService.updateItem(form.getId(), form);
        return "redirect:/items";

    }
}
