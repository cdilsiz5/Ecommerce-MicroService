package com.cihantech.productservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUpdateProductRequest {

    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;

    @NotBlank(message = "Description cannot be null")
    @Size(min = 2, max = 200, message = "Description must be between 2 and 200 characters")
    private String description;

    @DecimalMin(value = "0.1", inclusive = true, message = "Price must be at least 0.1")
    private BigDecimal price;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;
}
