package com.cihantech.categoryservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUpdateCategoryRequest {

    @NotBlank(message = "Category Name cannot be null")
    @Size(min = 2, max = 30, message = "ategory Name must be between 2 and 30 characters")
    private String categoryName;

    @NotBlank(message = "Category Description Name cannot be null")
    @Size(min = 2, max = 30, message = "Category Description Name must be between 2 and 30 characters")
    private String categoryDescription;

    @NotNull(message ="Parent Category ID Cannot Be Null")
    private Long parentCategoryId;

}
