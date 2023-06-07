package com.cihantech.orderservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUpdateOrderRequest {

    @NotNull(message = "User ID Cannot be null")
    private Long userId;
}
