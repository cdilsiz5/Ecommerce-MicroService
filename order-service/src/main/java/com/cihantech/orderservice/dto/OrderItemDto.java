package com.cihantech.orderservice.dto;

import com.cihantech.orderservice.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
    private Long id;

    private Order order;

    private Long productId;

    private int quantity;

    private BigDecimal pricePerUnit;

}
