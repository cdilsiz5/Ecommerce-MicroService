package com.cihantech.cartservice.repository;

import com.cihantech.cartservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userid);
}
