package com.ashu.ordermanagement.service;

import com.ashu.ordermanagement.dto.OrderResponse;
import com.ashu.ordermanagement.dto.OrderStatusRequest;

import org.springframework.data.domain.Page;

import com.ashu.ordermanagement.dto.OrderRequest;

public interface OrderService {

	 OrderResponse createOrder(OrderRequest request);
	 OrderResponse getOrderById(Long id);
	 
	 Page<OrderResponse> getAllOrders(
		        int page,
		        int size,
		        String sortBy,
		        String sortDir
		);
	 OrderResponse updateOrderStatus(
		        Long id,
		        OrderStatusRequest request
		);
	
}
