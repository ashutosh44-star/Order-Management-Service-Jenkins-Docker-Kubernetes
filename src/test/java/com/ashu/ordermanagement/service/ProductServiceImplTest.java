package com.ashu.ordermanagement.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ashu.ordermanagement.dto.ProductRequest;
import com.ashu.ordermanagement.dto.ProductResponse;
import com.ashu.ordermanagement.entity.Product;
import com.ashu.ordermanagement.mapper.ProductMapper;
import com.ashu.ordermanagement.repository.ProductRepository;
import com.ashu.ordermanagement.service.impl.ProductServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductMapper productMapper;
	@InjectMocks
	private ProductServiceImpl productService;

	@Test
	void testCreateProduct() {
		ProductRequest request = new ProductRequest();

		request.setName("Laptop");
		request.setDescription("Gaming Laptop");
		request.setPrice(new BigDecimal("75000"));
		request.setQuantity(10);
		request.setCategory("Electronics");
		Product product = new Product();

		product.setId(1L);
		product.setName("Laptop");
		product.setDescription("Gaming Laptop");
		product.setPrice(new BigDecimal("75000"));
		product.setQuantity(10);
		product.setCategory("Electronics");
		ProductResponse expectedResponse = new ProductResponse();

		expectedResponse.setId(1L);
		expectedResponse.setName("Laptop");
		expectedResponse.setDescription("Gaming Laptop");
		expectedResponse.setPrice(new BigDecimal("75000"));
		expectedResponse.setQuantity(10);
		expectedResponse.setCategory("Electronics");
		when(productMapper.toEntity(request)).thenReturn(product);

		when(productRepository.save(product)).thenReturn(product);

		when(productMapper.toResponse(product)).thenReturn(expectedResponse);
		ProductResponse actualResponse = productService.createProduct(request);
		
		assertNotNull(actualResponse);

        assertEquals(
                expectedResponse.getId(),
                actualResponse.getId()
        );

        assertEquals(
                expectedResponse.getName(),
                actualResponse.getName()
        );

        assertEquals(
                expectedResponse.getDescription(),
                actualResponse.getDescription()
        );

        assertEquals(
                expectedResponse.getPrice(),
                actualResponse.getPrice()
        );

        assertEquals(
                expectedResponse.getQuantity(),
                actualResponse.getQuantity()
        );

        assertEquals(
                expectedResponse.getCategory(),
                actualResponse.getCategory()
        );


        // =========================
        // 5. VERIFY
        // =========================

        verify(productMapper)
                .toEntity(request);

        verify(productRepository)
                .save(product);

        verify(productMapper)
                .toResponse(product);
	}

}