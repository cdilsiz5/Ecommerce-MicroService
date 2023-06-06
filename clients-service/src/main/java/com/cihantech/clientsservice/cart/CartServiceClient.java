package com.cihantech.clientsservice.cart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@FeignClient("cart-service")
public interface CartServiceClient {
    @GetMapping("/api/cart/getCart/{userId}")
    @ResponseStatus(HttpStatus.OK)
    CartDto getCartByUserId(@PathVariable(value = "userId") Long userId);
    @PutMapping("/api/cart/clearCart/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void clearCartByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/api/cart/getTotal/{userId}")
    @ResponseStatus(HttpStatus.OK)
    BigDecimal getTotal(@PathVariable("userId") Long userId);

}
