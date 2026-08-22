package com.ashu.ordermanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashu.ordermanagement.dto.OrderRequest;
import com.ashu.ordermanagement.dto.OrderResponse;
import com.ashu.ordermanagement.dto.OrderStatusRequest;
import com.ashu.ordermanagement.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest request) {

        OrderResponse response =
                orderService.createOrder(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id) {

        OrderResponse response =
                orderService.getOrderById(id);

        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "orderDate") String sortBy,

            @RequestParam(defaultValue = "desc") String sortDir
    ) {
    	 System.out.println("Order Management Service Version 2.1");
        Page<OrderResponse> response =
                orderService.getAllOrders(
                        page,
                        size,
                        sortBy,
                        sortDir
                );

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusRequest request) {

        OrderResponse response =
                orderService.updateOrderStatus(
                        id,
                        request
                );

        return ResponseEntity.ok(response);
    }
}