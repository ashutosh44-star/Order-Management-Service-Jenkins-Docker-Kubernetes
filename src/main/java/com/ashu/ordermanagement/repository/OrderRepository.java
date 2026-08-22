package com.ashu.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashu.ordermanagement.entity.Order;
public interface OrderRepository extends JpaRepository<Order, Long>{

}
