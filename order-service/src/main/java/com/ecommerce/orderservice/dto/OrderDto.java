package com.ecommerce.orderservice.dto;

import com.ecommerce.orderservice.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;

    private Long userId;

    private List<OrderItem> orderItems;

    private BigDecimal total;

    private Date orderDate;


}
