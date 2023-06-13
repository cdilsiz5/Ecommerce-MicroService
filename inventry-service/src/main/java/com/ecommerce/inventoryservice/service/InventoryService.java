package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.dto.InventoryDto;
import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.exception.NotFoundException;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import com.ecommerce.inventoryservice.request.CreateUpdateInventoryRequest;
import com.ecommerce.inventoryservice.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryDto getInventoryByProductId(Long productId) {
        log.info("Retrieving inventory for product id: {}", productId);
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> {
                    log.error("Inventory not found for product id: {}", productId);
                    return new NotFoundException("Inventory not found for product id " + productId);
                });
        log.info("Retrieved inventory: {}", inventory);
        return InventoryMapper.INVENTORY_MAPPER.toInventoryDto(inventory);
    }

    public List<InventoryDto> getInventoryList(){
        log.info("Retrieving all inventories");
        List<InventoryDto> inventories = InventoryMapper.INVENTORY_MAPPER.toInventoryDtoList(inventoryRepository.findAll());
        log.info("Retrieved {} inventories", inventories.size());
        return inventories;
    }

    public InventoryDto updateInventory(CreateUpdateInventoryRequest request) {
        log.info("Updating inventory for product id: {}", request.getProductId());
        Inventory inventory =  inventoryRepository.findByProductId(request.getProductId())
                .orElseThrow(() -> {
                    log.error("Inventory not found for product id: {}", request.getProductId());
                    return new NotFoundException("Inventory not found for product id " + request.getProductId());
                });
        inventory.setQuantity(inventory.getQuantity() - request.getQuantity());
        Inventory updatedInventory = inventoryRepository.save(inventory);
        log.info("Updated inventory: {}", updatedInventory);
        return InventoryMapper.INVENTORY_MAPPER.toInventoryDto(updatedInventory);
    }

    public InventoryDto createInventory(CreateUpdateInventoryRequest request){
        log.info("Creating inventory for product id: {}", request.getProductId());
        Inventory inventory= InventoryMapper.INVENTORY_MAPPER.createtoInventory(request);
        Inventory savedInventory = inventoryRepository.save(inventory);
        log.info("Created inventory: {}", savedInventory);
        return InventoryMapper.INVENTORY_MAPPER.toInventoryDto(savedInventory);
    }

    public void deleteInventoryProductId(Long productId){
        log.info("Deleting inventory for product id: {}", productId);
        inventoryRepository.deleteByProductId(productId);
        log.info("Deleted inventory for product id: {}", productId);
    }

    @Transactional(readOnly=true)
    @SneakyThrows
    public List<InventoryDto> isInStock(List<String> productIds) {
        log.info("Checking stock for product ids: {}", productIds);
        List<InventoryDto> inventories = inventoryRepository.findByProductIdIn(productIds)
                .stream()
                .map(inventory ->
                        InventoryDto.builder()
                                .productId(inventory.getProductId())
                                .quantity(inventory.getQuantity())
                                .id(inventory.getId())
                                .build()
                ).collect(Collectors.toList());
        log.info("Stock check result: {}", inventories);
        return inventories;
    }
}
