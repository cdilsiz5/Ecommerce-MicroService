package com.cihantech.cartservice.mapper;
import com.cihantech.cartservice.dto.CartDto;
import com.cihantech.cartservice.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartMapper CART_MAPPER= Mappers.getMapper(CartMapper.class);
    CartDto toCartDto(Cart Cart);

 }
