package com.ashu.ordermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ashu.ordermanagement.dto.OrderItemRequest;
import com.ashu.ordermanagement.dto.OrderItemResponse;
import com.ashu.ordermanagement.dto.OrderRequest;
import com.ashu.ordermanagement.dto.OrderResponse;
import com.ashu.ordermanagement.entity.Customer;
import com.ashu.ordermanagement.entity.Order;
import com.ashu.ordermanagement.entity.Product;
import com.ashu.ordermanagement.mapper.OrderMapper;
import com.ashu.ordermanagement.repository.CustomerRepository;
import com.ashu.ordermanagement.repository.OrderRepository;
import com.ashu.ordermanagement.repository.ProductRepository;
import com.ashu.ordermanagement.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void createOrder_success() {

        // =====================================
        // 1. CREATE REQUEST
        // =====================================

        OrderItemRequest itemRequest = new OrderItemRequest();

        itemRequest.setProductId(1L);
        itemRequest.setQuantity(2);


        OrderRequest request = new OrderRequest();

        request.setCustomerId(1L);
        request.setItems(List.of(itemRequest));


        // =====================================
        // 2. CREATE CUSTOMER
        // =====================================

        Customer customer = new Customer();

        customer.setId(1L);
        customer.setName("Ashu");
        customer.setEmail("ashu@test.com");
        customer.setPhone("9999999999");


        // =====================================
        // 3. CREATE PRODUCT
        // =====================================

        Product product = new Product();

        product.setId(1L);
        product.setName("Laptop");
        product.setDescription("Gaming Laptop");
        product.setPrice(new BigDecimal("75000"));
        product.setQuantity(10);
        product.setCategory("Electronics");


        // =====================================
        // 4. MOCK CUSTOMER REPOSITORY
        // =====================================

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));


        // =====================================
        // 5. MOCK PRODUCT REPOSITORY
        // =====================================

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));


        // =====================================
        // 6. MOCK ORDER SAVE
        // =====================================

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {

                    Order order = invocation.getArgument(0);

                    order.setId(1L);

                    return order;
                });


        // =====================================
        // 7. CREATE MAPPER RESPONSE
        // =====================================

        OrderItemResponse itemResponse =
                new OrderItemResponse();

        itemResponse.setProductId(1L);
        itemResponse.setProductName("Laptop");
        itemResponse.setQuantity(2);
        itemResponse.setUnitPrice(
                new BigDecimal("75000")
        );
        itemResponse.setSubtotal(
                new BigDecimal("150000")
        );


        OrderResponse mappedResponse =
                new OrderResponse();

        mappedResponse.setId(1L);
        mappedResponse.setCustomerId(1L);
        mappedResponse.setTotalAmount(
                new BigDecimal("150000")
        );
        mappedResponse.setItems(
                List.of(itemResponse)
        );


        // =====================================
        // 8. MOCK ORDER MAPPER
        // =====================================

        when(orderMapper.toOrderResponse(any(Order.class)))
                .thenReturn(mappedResponse);


        // =====================================
        // 9. ACT
        // =====================================

        OrderResponse response =
                orderService.createOrder(request);


        // =====================================
        // 10. ASSERT RESPONSE
        // =====================================

        assertNotNull(response);

        assertEquals(
                1L,
                response.getId()
        );

        assertEquals(
                1L,
                response.getCustomerId()
        );

        assertEquals(
                new BigDecimal("150000"),
                response.getTotalAmount()
        );


        // =====================================
        // 11. ASSERT ITEM
        // =====================================

        assertEquals(
                1,
                response.getItems().size()
        );

        assertEquals(
                1L,
                response.getItems().get(0).getProductId()
        );

        assertEquals(
                "Laptop",
                response.getItems().get(0).getProductName()
        );

        assertEquals(
                2,
                response.getItems().get(0).getQuantity()
        );

        assertEquals(
                new BigDecimal("75000"),
                response.getItems().get(0).getUnitPrice()
        );

        assertEquals(
                new BigDecimal("150000"),
                response.getItems().get(0).getSubtotal()
        );


        // =====================================
        // 12. ASSERT STOCK REDUCED
        // =====================================

        assertEquals(
                8,
                product.getQuantity()
        );


        // =====================================
        // 13. VERIFY REPOSITORY CALLS
        // =====================================

        verify(customerRepository)
                .findById(1L);

        verify(productRepository)
                .findById(1L);

        verify(orderRepository)
                .save(any(Order.class));


        // =====================================
        // 14. VERIFY MAPPER CALL
        // =====================================

        verify(orderMapper)
                .toOrderResponse(any(Order.class));
    }
}