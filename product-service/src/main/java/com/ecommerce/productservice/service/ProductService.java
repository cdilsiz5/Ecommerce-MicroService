package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.NotFoundException;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.request.CreateUpdateProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ecommerce.productservice.mapper.ProductMapper.PRODUCT_MAPPER;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public ProductDto createProduct(CreateUpdateProductRequest request) {
        log.info("Creating product with request: {}", request);
        Product product = PRODUCT_MAPPER.createProduct(request);
        Product savedProduct = repository.save(product);
        log.info("Created product: {}", savedProduct);
        return PRODUCT_MAPPER.toProductDto(savedProduct);
    }

    public ProductDto getProductById(Long id) {
        log.info("Retrieving product by id: {}", id);
        Product product = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);
                    return new NotFoundException("Product Not Found");
                });
        log.info("Retrieved product: {}", product);
        return PRODUCT_MAPPER.toProductDto(product);
    }

    public List<ProductDto> getProductList() {
        log.info("Retrieving all products");
        List<ProductDto> products = PRODUCT_MAPPER.toProductDtoList(repository.findAll());
        log.info("Retrieved {} products", products.size());
        return products;
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        repository.deleteById(id);
        log.info("Deleted product with id: {}", id);
    }

    public ProductDto updateProduct(Long id, CreateUpdateProductRequest request) {
        log.info("Updating product with id: {} and request: {}", id, request);
        Product product = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);
                    return new NotFoundException("Product Not Found");
                });
        PRODUCT_MAPPER.updateProduct(product, request);
        Product updatedProduct = repository.save(product);
        log.info("Updated product: {}", updatedProduct);
        return PRODUCT_MAPPER.toProductDto(updatedProduct);
    }
}
