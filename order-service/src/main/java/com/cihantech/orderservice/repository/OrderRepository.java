package com.cihantech.orderservice.repository;

import com.cihantech.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findOrderByUserId(Long userId);

}
