package com.cihantech.orderservice.service;

import com.cihantech.amqpservice.RabbitMqMessageProducer;
import com.cihantech.clientsservice.cart.CartDto;
import com.cihantech.clientsservice.cart.CartItemDto;
import com.cihantech.clientsservice.cart.CartServiceClient;
import com.cihantech.clientsservice.inventory.CreateUpdateInventoryRequest;
import com.cihantech.clientsservice.inventory.InventoryDto;
import com.cihantech.clientsservice.inventory.InventoryServiceClient;
import com.cihantech.clientsservice.notification.NotificationRequest;
import com.cihantech.clientsservice.product.ProductServiceClient;
import com.cihantech.clientsservice.user.UserServiceClient;
import com.cihantech.orderservice.dto.OrderDto;
import com.cihantech.orderservice.entity.Order;
import com.cihantech.orderservice.entity.OrderItem;
import com.cihantech.orderservice.enums.OrderStatus;
import com.cihantech.orderservice.exception.*;
import com.cihantech.orderservice.mapper.OrderMapper;
import com.cihantech.orderservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
 import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


    @Service
    @Slf4j
    @RequiredArgsConstructor
    public class OrderService {


    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final CartServiceClient cartServiceClient;
    private final InventoryServiceClient inventoryServiceClient;
    private final UserServiceClient userServiceClient;
    private final RabbitMqMessageProducer rabbitMqMessageProducer;


    @Transactional
    @Retry(name = "orderService", fallbackMethod = "fallbackForCreateOrder")
    @CircuitBreaker(name = "orderService", fallbackMethod = "fallbackForCreateOrder")
    public OrderDto createOrder(Long userId) {
        log.info("Creating order for user with id: {}", userId);
        try {
            CartDto cart = cartServiceClient.getCartByUserId(userId);
            BigDecimal cartTotal = calculateCartTotal(userId);

            Order order = prepareOrder(userId, cartTotal, cart);
            saveOrderAndUpdateCart(userId, order);

            log.info("Order created successfully for user with id: {}", userId);
            // todo: send notification message to user for creating order
            sendNotificaitonToUser(userId);

            return OrderMapper.Order_MAPPER.toOrderDto(order);

        } catch (Exception e) {
            log.error("Failed to create order for user with id: {}. Error: {}", userId, e.getMessage());
            throw new OrderCreationFailedException("Failed to create order for user with id: " + userId);
        }
    }

        private void sendNotificaitonToUser(Long userId) {
            NotificationRequest notificationRequest = NotificationRequest.builder()
                    .userId(userId)
                    .toUserEmail(userServiceClient.getUser(userId).getUserEmail())
                    .message("Your order has been purchased")
                    .build();
            rabbitMqMessageProducer.publish(
                    notificationRequest,
                    "internal.exchange",
                    "internal.notification.routing-key"
            );
        }

        private BigDecimal calculateCartTotal(Long userId) {
        return cartServiceClient.getTotal(userId);
    }

    private Order prepareOrder(Long userId, BigDecimal cartTotal, CartDto cart) {
        Order order = new Order();
        order.setUserId(userId);
        order.setTotal(cartTotal);
        order.setOrderItems(new ArrayList<OrderItem>());

        for (CartItemDto item : cart.getItems()) {
            addOrderItem(order, item);
        }

        order.setStatus(OrderStatus.CREATED);
        return order;
    }

    private void addOrderItem(Order order, CartItemDto item) {
        Long productId = item.getProductId();
        InventoryDto inventoryItem = inventoryServiceClient.getInventoryByProductId(productId);
        if (inventoryItem == null || inventoryItem.getQuantity() < item.getQuantity()) {
            throw new ItemNotAvailableException("Not enough quantity for product: " + productId);
        }
        BigDecimal pricePerUnit = productServiceClient.getProduct(productId).getPrice();
        OrderItem orderItem = OrderItem.builder()
                .quantity(item.getQuantity())
                .pricePerUnit(pricePerUnit)
                .productId(item.getProductId())
                .build();
        order.getOrderItems().add(orderItem);

        inventoryServiceClient.updateInventory(CreateUpdateInventoryRequest.builder()
                .productId(productId)
                .quantity(item.getQuantity())
                .build());
    }

    private void saveOrderAndUpdateCart(Long userId, Order order) {
        order = orderRepository.save(order);
        cartServiceClient.clearCartByUserId(userId);
    }


    @Transactional
    public OrderDto cancelOrder(Long orderId) {
        log.info("Canceling order with id: {}", orderId);
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new NotFoundException("Order with id: " + orderId + " not found"));

            if (!order.getStatus().equals(OrderStatus.CREATED)) {
                throw new CannotCancelOrderException("Cannot cancel order with status: " + order.getStatus());
            }

            order.setStatus(OrderStatus.CANCELLED);
            order = orderRepository.save(order);

            for (OrderItem orderItem : order.getOrderItems()) {
                inventoryServiceClient.updateInventory(CreateUpdateInventoryRequest.builder()
                        .productId(orderItem.getProductId())
                        .quantity(orderItem.getQuantity())
                        .build());
            }

            log.info("Order with id: {} cancelled successfully", orderId);
            return OrderMapper.Order_MAPPER.toOrderDto(order);
        } catch (Exception e) {
            log.error("Failed to cancel order with id: {}. Error: {}", orderId, e.getMessage());
            throw new OrderCancellationFailedException("Failed to cancel order with id: " + orderId);
        }
    }

    public List<OrderDto> getOrdersByUserIdAndStatus(Long userId) {
        log.info("Retrieving orders for user with id: {} and status: {}", userId);
        List<Order> orders = orderRepository.findOrderByUserId(userId);
        return OrderMapper.Order_MAPPER.toOrderDtoList(orders);

    }
    public OrderStatus checkOrderStatus(Long orderId) {
        log.info("Checking status for order with id: {}", orderId);
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new NotFoundException("Order with id: " + orderId + " not found"));
            log.info("Status for order with id: {} is {}", orderId, order.getStatus());
            return order.getStatus();
    }
        public OrderDto fallbackForCreateOrder(Long userId, Exception e) {
            log.error("Failed to create order for user with id: {}", userId, e);
            throw new OrderCreationFailedException("Order creation failed for user with id: " + userId);
        }



    }

