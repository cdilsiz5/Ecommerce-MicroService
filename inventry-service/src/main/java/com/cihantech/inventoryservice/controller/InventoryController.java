package com.cihantech.inventoryservice.controller;

import com.cihantech.inventoryservice.dto.InventoryDto;
import com.cihantech.inventoryservice.request.CreateUpdateInventoryRequest;
import com.cihantech.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryDto createInventory(@Valid @RequestBody  CreateUpdateInventoryRequest request){
        return service.createInventory(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<InventoryDto> getStockList( List<String> productId){
        return service.isInStock(productId);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/inventory-list")
    public List<InventoryDto> getInventoryList( ){
        return service.getInventoryList();
    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public InventoryDto updateInventory(@Valid @RequestBody CreateUpdateInventoryRequest request){
        return service.updateInventory(request);
    }
    @DeleteMapping("{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable Long productId){
        service.deleteInventoryProductId(productId);
    }
    @GetMapping("{productId}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryDto getInventoryByProductId(@PathVariable("productId") Long productId){
        return service.getInventoryByProductId(productId);
    }

}
