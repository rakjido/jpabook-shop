package io.rooftop.jpashop.web;

import io.rooftop.jpashop.domain.Member;
import io.rooftop.jpashop.domain.Order;
import io.rooftop.jpashop.domain.item.Item;
import io.rooftop.jpashop.repository.OrderSearch;
import io.rooftop.jpashop.service.ItemService;
import io.rooftop.jpashop.service.MemberService;
import io.rooftop.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        log.info(getClass().getName() + " / " + methodName);

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        log.info(getClass().getName() + " / " + methodName);

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        log.info(getClass().getName() + " / " + methodName);

        List<Order> orders = orderService.findOrders(orderSearch);
//        System.out.println("orders.get(0).getStatus() = " + orders.get(0).getStatus());
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
