package com.ecommerce.clientsservice.inventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUpdateInventoryRequest {

    private Long productId;

    private int quantity;

}