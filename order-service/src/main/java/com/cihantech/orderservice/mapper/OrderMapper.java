package com.cihantech.orderservice.mapper;

import com.cihantech.orderservice.dto.OrderDto;
import com.cihantech.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;
@Mapper(componentModel = "spring")
public interface OrderMapper {
        OrderMapper Order_MAPPER= Mappers.getMapper(OrderMapper.class);
        OrderDto toOrderDto(Order order);
        List<OrderDto> toOrderDtoList(List<Order> OrderList);
    }

