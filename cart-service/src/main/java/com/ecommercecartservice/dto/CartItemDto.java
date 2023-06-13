package com.ecommercecartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
 public class CartItemDto {

    private Long id;


    private Long productId;


    private int quantity;

}
