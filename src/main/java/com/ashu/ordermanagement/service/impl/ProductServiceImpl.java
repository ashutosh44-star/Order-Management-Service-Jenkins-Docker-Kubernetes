package com.ashu.ordermanagement.service.impl;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Service;

import com.ashu.ordermanagement.dto.ProductRequest;
import com.ashu.ordermanagement.dto.ProductResponse;
import com.ashu.ordermanagement.entity.Product;
import com.ashu.ordermanagement.exception.ProductNotFoundException;
import com.ashu.ordermanagement.mapper.ProductMapper;
import com.ashu.ordermanagement.repository.ProductRepository;
import com.ashu.ordermanagement.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private final ProductMapper productMapper;

	
	 public ProductServiceImpl(ProductRepository productRepository,ProductMapper productMapper) {
	        this.productRepository = productRepository;
	        this.productMapper = productMapper;
	    }
	
	@Override
	public ProductResponse createProduct(ProductRequest request) {
		Product product = productMapper.toEntity(request);

	    Product savedProduct = productRepository.save(product);

	    return productMapper.toResponse(savedProduct);
	    
	}

	@Override
	public List<ProductResponse> getAllProducts() {

		 return productRepository.findAll()
		            .stream()
		            .map(productMapper::toResponse)
		            .toList();
	}

	@Override
	public ProductResponse getProductById(Long id) {

	    Product product = productRepository.findById(id)
	            .orElseThrow(() ->
	            new ProductNotFoundException(
                        "Product not found with id: " + id));

	    return productMapper.toResponse(product);
	}

	@Override
	public ProductResponse updateProduct(Long id, ProductRequest request) {

	    Product product = productRepository.findById(id)
	            .orElseThrow(() ->
	                    new ProductNotFoundException(
	                            "Product not found with id: " + id));

	    product.setName(request.getName());
	    product.setDescription(request.getDescription());
	    product.setPrice(request.getPrice());
	    product.setQuantity(request.getQuantity());
	    product.setCategory(request.getCategory());

	    Product updatedProduct = productRepository.save(product);

	    return productMapper.toResponse(updatedProduct);
	}

	@Override
	public void deleteProduct(Long id) {

	    Product product = productRepository.findById(id)
	            .orElseThrow(() ->
	                    new ProductNotFoundException(
	                            "Product not found with id: " + id));

	    productRepository.delete(product);
	}

}
