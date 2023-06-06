package com.cihantech.clientsservice.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("inventory-service")
public interface InventoryServiceClient {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/inventory")
    List<InventoryDto> isImStock(@RequestParam("productId") List<String> productId);
    @GetMapping("/api/inventory/{productId}")
    @ResponseStatus(HttpStatus.OK)
    InventoryDto getInventoryByProductId(@PathVariable("productId") Long productId);

    @PutMapping("/api/inventory")
    @ResponseStatus(HttpStatus.OK)
    InventoryDto updateInventory(@RequestBody CreateUpdateInventoryRequest request);
}
