package com.ecommercecartservice.repository;

import com.ecommercecartservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userid);
}
