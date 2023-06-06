package com.cihantech.categoryservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUpdateCategoryRequest {

    @NotNull(message ="Name Cannot Be Null")
    private String categoryName;

    @NotNull(message ="Description Cannot Be Null")
    private String categoryDescription;

    @NotNull(message ="Parent Category Id  Cannot Be Null")
    private Long parentCategoryId;

}
