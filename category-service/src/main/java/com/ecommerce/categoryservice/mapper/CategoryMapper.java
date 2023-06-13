package com.ecommerce.categoryservice.mapper;

import com.ecommerce.categoryservice.dto.CategoryDto;
import com.ecommerce.categoryservice.entity.Category;
import com.ecommerce.categoryservice.request.CreateUpdateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper CATEGORY_MAPPER= Mappers.getMapper(CategoryMapper.class);

    CategoryDto toCategoryDto(Category category);

    List<CategoryDto> toCategoryDtoList(List<Category> categoryList);

    Category createCategory(CreateUpdateCategoryRequest request);

    void  updateCategory(@MappingTarget Category category, CreateUpdateCategoryRequest request);
}
