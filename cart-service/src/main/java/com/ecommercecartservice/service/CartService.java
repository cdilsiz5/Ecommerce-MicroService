package com.ecommercecartservice.service;

import com.ecommerce.clientsservice.user.UserDto;
import com.ecommerce.clientsservice.user.UserServiceClient;
import com.ecommercecartservice.dto.CartDto;
import com.ecommercecartservice.entity.Cart;
import com.ecommercecartservice.entity.CartItem;
import com.ecommercecartservice.exception.NotFoundException;
import com.ecommercecartservice.mapper.CartMapper;
import com.ecommercecartservice.repository.CartRepository;
import com.ecommercecartservice.request.CreateUpdateCartRequest;
import com.ecommerce.clientsservice.product.ProductServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;


@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository repository;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;
    public CartDto addItem(Long userId, CreateUpdateCartRequest request) {
        if(!checkIfUserExist(userId)){
            throw new NotFoundException("User Not Found");
        }
        log.info("Adding item to the cart for user id: {}", userId);
        CartItem item = CartItem.builder().productId(request.getProductId()).quantity(request.getQuantity()).build();
        Cart cart = repository.findByUserId(userId);
        if (cart == null) {
            cart = saveCart(userId);
        }
        cart.getItems().add(item);
        Cart updatedCart = repository.save(cart);
        log.info("Item added to the cart successfully for user id: {}", userId);
        return CartMapper.CART_MAPPER.toCartDto(updatedCart);
    }

    public CartDto removeItem(Long userId, Long itemId) {
        if(!checkIfUserExist(userId)){
            throw new NotFoundException("User Not Found");
        }
        log.info("Removing item from the cart for user id: {}", userId);
        Cart cart = repository.findByUserId(userId);
        if (cart == null) {
            throw new NotFoundException("Cart not found for user with id: " + userId);
        }
        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        Cart updatedCart = repository.save(cart);
        log.info("Item removed from the cart successfully for user id: {}", userId);
        return CartMapper.CART_MAPPER.toCartDto(updatedCart);
    }

    public CartDto updateItemQuantity(Long userId, CreateUpdateCartRequest request) {
        if(!checkIfUserExist(userId)){
            throw new NotFoundException("User Not Found");
        }
        log.info("Updating item quantity in the cart for user id: {}", userId);
        Cart cart = repository.findByUserId(userId);
        if (cart == null) {
            throw new NotFoundException("Cart not found for user with id: " + userId);
        }
        cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .forEach(item -> item.setQuantity(request.getQuantity()));
        Cart updatedCart = repository.save(cart);
        log.info("Item quantity updated in the cart successfully for user id: {}", userId);
        return CartMapper.CART_MAPPER.toCartDto(updatedCart);
    }

    public CartDto getCartDtoByUserId(Long userId) {
        if(!checkIfUserExist(userId)){
            throw new NotFoundException("User Not Found");
        }
        log.info("Retrieving cart for user id: {}", userId);
        Cart cart = repository.findByUserId(userId);
        if (cart == null) {
            cart = saveCart(userId);
        }
        log.info("Cart retrieved successfully for user id: {}", userId);
        return CartMapper.CART_MAPPER.toCartDto(cart);
    }

    public void clearCartByUserId(Long userId) {
        if(!checkIfUserExist(userId)){
            throw new NotFoundException("User Not Found");
        }
        log.info("Clearing cart for user id: {}", userId);
        Cart cart = repository.findByUserId(userId);
        if (cart == null) {
            cart = saveCart(userId);
        } else {
            cart.setItems(new ArrayList<CartItem>());
            repository.save(cart);
        }
        log.info("Cart cleared successfully for user id: {}", userId);
    }

    public Cart saveCart(Long userId) {
        log.info("Saving cart for user id: {}", userId);
        if(!checkIfUserExist(userId)){
            throw new NotFoundException("User Not Found");
        }
        Cart cart = Cart.builder()
                .userId(userId)
                .items(new ArrayList<CartItem>())
                .build();
        Cart savedCart = repository.save(cart);
        log.info("Cart saved successfully for user id: {}", userId);
        return savedCart;
    }

    private Boolean checkIfUserExist(Long userId) {
        UserDto user=userServiceClient.getUser(userId);
        if(user==null)
            return false;
        return true;


    }

    @CircuitBreaker(name = "cartService", fallbackMethod = "fallbackForCartTotal")
    @Retry(name = "cartService", fallbackMethod = "fallbackForCartTotal")
    public BigDecimal getTotal(Long userId) {
        if(!checkIfUserExist(userId)){
            throw new NotFoundException("User Not Found");
        }
        log.info("Calculating total for the cart for user id: {}",userId);
        Cart cart = repository.findByUserId(userId);
        if (cart == null) {
            log.warn("Cart not found for user id: {}. Saving new cart.", userId);
            cart = saveCart(userId);
            return BigDecimal.ZERO;
        }
        BigDecimal total = cart.getItems().stream()
                .map(item ->
                        productServiceClient.getProduct(item.getProductId()).getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Total calculated successfully for the cart for user id: {}", userId);
        return total;
    }

    public BigDecimal fallbackForCartTotal(Long userId, Exception e){
        log.error("Failed to get cart total for user with id: {}. Error: {}", userId, e.getMessage());
        throw new RuntimeException("System is not working right now please try after some time");
    }
}

