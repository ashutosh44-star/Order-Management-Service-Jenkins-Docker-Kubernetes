package com.ashu.ordermanagement.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ashu.ordermanagement.dto.OrderItemRequest;
import com.ashu.ordermanagement.dto.OrderRequest;
import com.ashu.ordermanagement.dto.OrderResponse;
import com.ashu.ordermanagement.dto.OrderStatusRequest;
import com.ashu.ordermanagement.entity.Customer;
import com.ashu.ordermanagement.entity.Order;
import com.ashu.ordermanagement.entity.OrderItem;
import com.ashu.ordermanagement.entity.OrderStatus;
import com.ashu.ordermanagement.entity.Product;
import com.ashu.ordermanagement.exception.CustomerNotFoundException;
import com.ashu.ordermanagement.exception.InsufficientStockException;
import com.ashu.ordermanagement.exception.OrderNotFoundException;
import com.ashu.ordermanagement.exception.ProductNotFoundException;
import com.ashu.ordermanagement.mapper.OrderMapper;
import com.ashu.ordermanagement.repository.CustomerRepository;
import com.ashu.ordermanagement.repository.OrderRepository;
import com.ashu.ordermanagement.repository.ProductRepository;
import com.ashu.ordermanagement.service.OrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
public class OrderServiceImpl implements OrderService{
	
	private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository,
            OrderMapper orderMapper) {

        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }
	@Override
	@Transactional
	public OrderResponse createOrder(OrderRequest request) {
		Customer customer = customerRepository
		        .findById(request.getCustomerId())
		        .orElseThrow(() -> new CustomerNotFoundException(
		                "Customer not found with id: " + request.getCustomerId()
		        ));
		Order order = new Order();

		order.setOrderNumber(
		        "ORD-" + UUID.randomUUID()
		);

		order.setOrderDate(LocalDateTime.now());

		order.setStatus(OrderStatus.PENDING);

		order.setTotalAmount(BigDecimal.ZERO);

		order.setCustomer(customer);
	    BigDecimal totalAmount = BigDecimal.ZERO;
		for (OrderItemRequest itemRequest : request.getItems()) {

            Product product = productRepository
                    .findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(
                            "Product not found with id: "
                                    + itemRequest.getProductId()
                    ));

            if (product.getQuantity() < itemRequest.getQuantity()) {

                throw new InsufficientStockException(
                        "Insufficient stock for product: "
                                + product.getName()
                );
            }
            BigDecimal unitPrice = product.getPrice();

            BigDecimal subtotal = unitPrice.multiply(
                    BigDecimal.valueOf(
                            itemRequest.getQuantity()
                    )
            );
            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(unitPrice);
            orderItem.setSubtotal(subtotal);

            order.addItem(orderItem);

            product.setQuantity(
                    product.getQuantity()
                            - itemRequest.getQuantity()
            );

            totalAmount = totalAmount.add(subtotal);
		 }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toOrderResponse(savedOrder);
	}
	
	@Override
	public OrderResponse getOrderById(Long id) {

	    Order order = orderRepository
	            .findById(id)
	            .orElseThrow(() ->
	                    new OrderNotFoundException(
	                            "Order not found with id: " + id
	                    )
	            );

	    return orderMapper.toOrderResponse(order);
	}
	@Override
	public Page<OrderResponse> getAllOrders(
	        int page,
	        int size,
	        String sortBy,
	        String sortDir) {

	    Sort sort;

	    if (sortDir.equalsIgnoreCase("asc")) {

	        sort = Sort.by(sortBy).ascending();

	    } else {

	        sort = Sort.by(sortBy).descending();
	    }

	    Pageable pageable =
	            PageRequest.of(page, size, sort);

	    Page<Order> orders =
	            orderRepository.findAll(pageable);

	    return orders.map(orderMapper::toOrderResponse);
	}
	@Override
	@Transactional
	public OrderResponse updateOrderStatus(
	        Long id,
	        OrderStatusRequest request) {

	    Order order = orderRepository
	            .findById(id)
	            .orElseThrow(() ->
	                    new OrderNotFoundException(
	                            "Order not found with id: " + id
	                    )
	            );

	    OrderStatus currentStatus = order.getStatus();

	    OrderStatus newStatus = request.getStatus();

	    validateStatusTransition(
	            currentStatus,
	            newStatus
	    );

	    order.setStatus(newStatus);

	    return orderMapper.toOrderResponse(order);
	}
	private void validateStatusTransition(
	        OrderStatus currentStatus,
	        OrderStatus newStatus) {

	    switch (currentStatus) {

	        case PENDING:

	            if (newStatus != OrderStatus.CONFIRMED
	                    && newStatus != OrderStatus.CANCELLED) {

	                throw new IllegalStateException(
	                        "Invalid status transition from "
	                                + currentStatus
	                                + " to "
	                                + newStatus
	                );
	            }

	            break;

	        case CONFIRMED:

	            if (newStatus != OrderStatus.SHIPPED
	                    && newStatus != OrderStatus.CANCELLED) {

	                throw new IllegalStateException(
	                        "Invalid status transition from "
	                                + currentStatus
	                                + " to "
	                                + newStatus
	                );
	            }

	            break;

	        case SHIPPED:

	            if (newStatus != OrderStatus.DELIVERED) {

	                throw new IllegalStateException(
	                        "Invalid status transition from "
	                                + currentStatus
	                                + " to "
	                                + newStatus
	                );
	            }

	            break;

	        case DELIVERED:
	        case CANCELLED:

	            throw new IllegalStateException(
	                    "Order with status "
	                            + currentStatus
	                            + " cannot be updated"
	            );
	    }
	}
}
