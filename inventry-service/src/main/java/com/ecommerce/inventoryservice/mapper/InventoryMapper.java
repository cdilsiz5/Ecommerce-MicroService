package com.ecommerce.inventoryservice.mapper;

import com.ecommerce.inventoryservice.dto.InventoryDto;
import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.request.CreateUpdateInventoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryMapper INVENTORY_MAPPER= Mappers.getMapper(InventoryMapper.class);


    InventoryDto toInventoryDto(Inventory inventory);
    List<InventoryDto> toInventoryDtoList(List<Inventory> productList);

    Inventory createtoInventory(CreateUpdateInventoryRequest request);

    void updateInventory(@MappingTarget Inventory inventory, CreateUpdateInventoryRequest request);
}

