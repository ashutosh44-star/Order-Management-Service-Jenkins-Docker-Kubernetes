package com.ashu.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashu.ordermanagement.entity.OrderItem;
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
