package com.ashu.ordermanagement.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ashu.ordermanagement.entity.Customer;
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
