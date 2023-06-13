package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderDto;
import com.ecommerce.orderservice.enums.OrderStatus;
import com.ecommerce.orderservice.request.CreateUpdateOrderRequest;
import com.ecommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public OrderDto createOrder(@Valid @RequestBody CreateUpdateOrderRequest request) {
        return orderService.createOrder(request.getUserId());
    }

    @PutMapping("/{orderId}/cancel")
    public OrderDto cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    @GetMapping("/{orderId}/status")
    public OrderStatus checkOrderStatus(@PathVariable Long orderId) {
        return orderService.checkOrderStatus(orderId);
    }

    @GetMapping
    public List<OrderDto> getOrdersByUserIdAndStatus(@RequestParam Long userId) {
        return orderService.getOrdersByUserIdAndStatus(userId);
    }
}
