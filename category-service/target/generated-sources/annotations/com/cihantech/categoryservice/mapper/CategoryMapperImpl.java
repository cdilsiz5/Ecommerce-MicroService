package com.cihantech.categoryservice.mapper;

import com.cihantech.categoryservice.dto.CategoryDto;
import com.cihantech.categoryservice.entity.Category;
import com.cihantech.categoryservice.request.CreateUpdateCategoryRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-30T03:47:29-0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto toCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.id( category.getId() );
        categoryDto.categoryName( category.getCategoryName() );
        categoryDto.categoryDescription( category.getCategoryDescription() );
        categoryDto.parentCategoryId( category.getParentCategoryId() );

        return categoryDto.build();
    }

    @Override
    public List<CategoryDto> toCategoryDtoList(List<Category> categoryList) {
        if ( categoryList == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( categoryList.size() );
        for ( Category category : categoryList ) {
            list.add( toCategoryDto( category ) );
        }

        return list;
    }

    @Override
    public Category createCategory(CreateUpdateCategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        Category.CategoryBuilder<?, ?> category = Category.builder();

        category.categoryName( request.getCategoryName() );
        category.categoryDescription( request.getCategoryDescription() );
        category.parentCategoryId( request.getParentCategoryId() );

        return category.build();
    }

    @Override
    public void updateCategory(Category category, CreateUpdateCategoryRequest request) {
        if ( request == null ) {
            return;
        }

        category.setCategoryName( request.getCategoryName() );
        category.setCategoryDescription( request.getCategoryDescription() );
        category.setParentCategoryId( request.getParentCategoryId() );
    }
}