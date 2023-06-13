package com.ecommerce.categoryservice.repository;

import com.ecommerce.categoryservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
