package com.ecommerce.categoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
 public class CategoryDto {

    private Integer id;

    private String categoryName;

    private String categoryDescription;

    private Long parentCategoryId;
}