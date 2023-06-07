package com.cihantech.productservice.controller;

import com.cihantech.productservice.dto.ProductDto;
import com.cihantech.productservice.request.CreateUpdateProductRequest;
import com.cihantech.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequestMapping("/api/product")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDto createProduct(@Valid @RequestBody CreateUpdateProductRequest request){
        return  service.createProduct(request);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getProductList(){
        return  service.getProductList();
    }
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable Long id){
        return service.getProductById(id);
    }
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@PathVariable Long id,@Valid @RequestBody CreateUpdateProductRequest request){
        return service.updateProduct(id,request);
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);
    }



}
