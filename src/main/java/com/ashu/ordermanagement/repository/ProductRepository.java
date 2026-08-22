package com.ashu.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashu.ordermanagement.entity.Product;
public interface ProductRepository extends JpaRepository<Product, Long> {

}
