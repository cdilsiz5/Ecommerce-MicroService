package com.ecommercecartservice.controller;

import com.ecommercecartservice.dto.CartDto;
import com.ecommercecartservice.request.CreateUpdateCartRequest;
import com.ecommercecartservice.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("{userId}")
    public ResponseEntity<CartDto> addItem(@PathVariable Long userId, @Valid @RequestBody CreateUpdateCartRequest request) {
        CartDto updatedCart = cartService.addItem(userId, request);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CartDto> removeItem(@PathVariable Long userId, @PathVariable Long itemId) {
        CartDto updatedCart = cartService.removeItem(userId, itemId);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartDto> updateItemQuantity(@PathVariable Long userId,@Valid @RequestBody CreateUpdateCartRequest request) {
        CartDto updatedCart = cartService.updateItemQuantity(userId, request);
        return ResponseEntity.ok(updatedCart);
    }
    @GetMapping("/getCart/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public CartDto getCartByUserId(@PathVariable Long userId){
        return cartService.getCartDtoByUserId(userId);
    }
    @PutMapping("/clearCart/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCartByUserId(@PathVariable Long userId){
        cartService.clearCartByUserId(userId);
    }
    @GetMapping("/getTotal/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getTotal(@PathVariable Long userId){
        return cartService.getTotal(userId);
    }

}
