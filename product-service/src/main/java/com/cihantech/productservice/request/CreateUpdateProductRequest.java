package com.cihantech.productservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUpdateProductRequest {

    private String name;

    private String description;

    private BigDecimal price;

    private Long categoryId;
}
