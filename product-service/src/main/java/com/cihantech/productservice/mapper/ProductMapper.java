package com.cihantech.productservice.mapper;

import com.cihantech.productservice.dto.ProductDto;
import com.cihantech.productservice.entity.Product;
import com.cihantech.productservice.request.CreateUpdateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper PRODUCT_MAPPER= Mappers.getMapper(ProductMapper.class);

    ProductDto toProductDto(Product product);
    List<ProductDto> toProductDtoList(List<Product> productList);

    Product createProduct(CreateUpdateProductRequest request);

    void updateProduct(@MappingTarget Product product,CreateUpdateProductRequest request);
}
