package com.cihantech.categoryservice.controller;

import com.cihantech.categoryservice.dto.CategoryDto;
import com.cihantech.categoryservice.request.CreateUpdateCategoryRequest;
import com.cihantech.categoryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor

public class CategoryController {

    private final CategoryService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CategoryDto createCategory(@Valid @RequestBody CreateUpdateCategoryRequest request){
        System.out.println(request.toString());
        return  service.createCategory(request);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategoryList(){
        return  service.getCategoryList();
    }
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategory(@PathVariable Long id){
        return service.getCategoryById(id);
    }
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CreateUpdateCategoryRequest request){
        return service.updateCategory(id,request);
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id){
        service.deleteCategory(id);
    }


}
