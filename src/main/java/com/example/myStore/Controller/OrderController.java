package com.example.myStore.Controller;

import com.example.myStore.Domain.Cart;
import com.example.myStore.Domain.Order;
import com.example.myStore.Domain.Product;
import com.example.myStore.Domain.User;
import com.example.myStore.Repository.CartRepository;
import com.example.myStore.Repository.OrderRepository;
import com.example.myStore.Repository.ProdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    private ProdRepository prodRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping
    public String getOrder(
            Map<String, Object> model
    ) {
        Order order = getCurrentOrder();
        Integer balance = order.getCart().getCashBalance();
        model.put("balance", balance);

        if (order.getOrderMap().size() != 0) {
            Integer orderCost = 0;
            Product prodFromOrder;
            Long prodCount;

            Map<Product, Long> products = new LinkedHashMap<>();
            for (Long curProdID : order.getOrderMap().keySet()) {
                Optional<Product> optional = prodRepository.findById(curProdID);
                if (optional.isPresent()) {
                    prodFromOrder = optional.get();
                    prodCount = order.getOrderMap().get(curProdID);
                    products.put(prodFromOrder, prodCount);
                    orderCost = orderCost + (prodFromOrder.getCost() * prodCount.intValue());
                    //mnogo mathematiki nado add
                }
                /*else {
                tipa esli udalit` product iz bd to on saved on orderMap
                    order.getOrderMap().remove(curProdID);
                }*/
            }
            model.put("products", products);
            model.put("order", order);
            model.put("orderCost", orderCost);
            order.setCost(orderCost);
            orderRepository.save(order);
        }
        return "orderPage";
    }

    @PostMapping("/deleteProdFromOrder")
    public String delete(
            @RequestParam Map<String, String> form
    ) {
        Order order = getCurrentOrder();
        if (order != null) {
            for (String key : form.keySet()) {
                if (key.contains("id")) {
                    if (1L == order.getOrderMap().get(Long.valueOf(form.get("id")))) {
                        order.getOrderMap().remove(Long.valueOf(form.get("id")));
                    } else {
                        Long count = order.getOrderMap().get(Long.valueOf(form.get("id")));
                        order.getOrderMap().put(Long.valueOf(form.get("id")), --count);
                    }
                    break;
                }
            }
            orderRepository.save(order);
        }
        return "redirect:/order";
    }

    @PostMapping("/buyOrder")
    public String buy() {
        Order order = getCurrentOrder();
        Cart cart = order.getCart();
        int balance = cart.getCashBalance();
        int cost = order.getCost();

        if (balance >= cost) {
            order.setActive(false);
            cart.setCashBalance(balance - cost);
            orderRepository.save(order);
        } /*else {
            return "addBalancePage";
        }*/
        return "redirect:/order";
    }

    public Order getCurrentOrder(
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = cartRepository.findById(user.getCart().getId()).get();
        if (cart.getActualOrder() == null) {
            Order order = new Order(cart);
            cart.setOrdersList(Arrays.asList(order));
            orderRepository.save(order);
            return order;
        } else {
            return cart.getActualOrder();
        }
    }
}


