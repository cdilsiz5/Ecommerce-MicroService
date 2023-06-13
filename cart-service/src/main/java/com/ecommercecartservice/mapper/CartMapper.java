package com.ecommercecartservice.mapper;
import com.ecommercecartservice.dto.CartDto;
import com.ecommercecartservice.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartMapper CART_MAPPER= Mappers.getMapper(CartMapper.class);
    CartDto toCartDto(Cart Cart);

 }
