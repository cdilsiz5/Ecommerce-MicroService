package com.ecommerce.clientsservice.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient("product-service")
public interface ProductServiceClient {
    @GetMapping("/api/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductDto getProduct(@PathVariable(value = "id") Long id);
}
