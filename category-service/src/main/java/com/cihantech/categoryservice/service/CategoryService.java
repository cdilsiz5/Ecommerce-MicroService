package com.cihantech.categoryservice.service;

import com.cihantech.categoryservice.dto.CategoryDto;
import com.cihantech.categoryservice.entity.Category;
import com.cihantech.categoryservice.exception.NotFoundException;
import com.cihantech.categoryservice.repository.CategoryRepository;
import com.cihantech.categoryservice.request.CreateUpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cihantech.categoryservice.mapper.CategoryMapper.CATEGORY_MAPPER;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryDto createCategory(CreateUpdateCategoryRequest request){
        log.info("Creating a new category: {}", request);
        Category category= CATEGORY_MAPPER.createCategory(request);
        Category savedCategory = repository.save(category);
        log.info("Created category: {}", savedCategory);
        return CATEGORY_MAPPER.toCategoryDto(savedCategory);
    }

    public List<CategoryDto> getCategoryList(){
        log.info("Retrieving all categories");
        List<CategoryDto> categories = CATEGORY_MAPPER.toCategoryDtoList(repository.findAll());
        log.info("Retrieved {} categories", categories.size());
        return categories;
    }

    public CategoryDto getCategoryById(Long id){
        log.info("Retrieving category with id: {}", id);
        Category category = repository.findById(id).orElseThrow(() -> {
            log.error("Category not found with id: {}", id);
            return new NotFoundException("Category Not Found");
        });
        log.info("Retrieved category: {}", category);
        return CATEGORY_MAPPER.toCategoryDto(category);
    }

    public CategoryDto updateCategory(Long id, CreateUpdateCategoryRequest request){
        log.info("Updating category with id: {}", id);
        Category category=repository.findById(id).orElseThrow(() -> {
            log.error("Category not found with id: {}", id);
            return new NotFoundException("Category Not Found");
        });
        CATEGORY_MAPPER.updateCategory(category, request);
        Category updatedCategory = repository.save(category);
        log.info("Updated category: {}", updatedCategory);
        return CATEGORY_MAPPER.toCategoryDto(updatedCategory);
    }

    public void deleteCategory(Long id){
        log.info("Deleting category with id: {}", id);
        repository.deleteById(id);
        log.info("Deleted category with id: {}", id);
    }
}




