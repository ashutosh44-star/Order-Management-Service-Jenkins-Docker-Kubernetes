package com.ashu.ordermanagement.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ashu.ordermanagement.dto.OrderItemResponse;
import com.ashu.ordermanagement.dto.OrderResponse;
import com.ashu.ordermanagement.entity.Order;
import com.ashu.ordermanagement.entity.OrderItem;

@Component
public class OrderMapper {

    public OrderResponse toOrderResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setId(order.getId());

        response.setOrderNumber(order.getOrderNumber());

        response.setCustomerId(
                order.getCustomer().getId()
        );

        response.setStatus(order.getStatus());

        response.setTotalAmount(
                order.getTotalAmount()
        );

        response.setOrderDate(
                order.getOrderDate()
        );

        List<OrderItemResponse> itemResponses =
                order.getItems()
                        .stream()
                        .map(this::toOrderItemResponse)
                        .collect(Collectors.toList());

        response.setItems(itemResponses);

        return response;
    }

    private OrderItemResponse toOrderItemResponse(
            OrderItem orderItem) {

        OrderItemResponse response =
                new OrderItemResponse();

        response.setProductId(
                orderItem.getProduct().getId()
        );

        response.setProductName(
                orderItem.getProduct().getName()
        );

        response.setQuantity(
                orderItem.getQuantity()
        );

        response.setUnitPrice(
                orderItem.getUnitPrice()
        );

        response.setSubtotal(
                orderItem.getSubtotal()
        );

        return response;
    }
}