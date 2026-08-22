package com.ashu.ordermanagement.service;

import java.util.List;

import com.ashu.ordermanagement.dto.ProductRequest;
import com.ashu.ordermanagement.dto.ProductResponse;
public interface ProductService {
	
	ProductResponse createProduct(ProductRequest product);
	
	List<ProductResponse> getAllProducts();
	
	ProductResponse getProductById(Long id);
	
	ProductResponse updateProduct(Long id, ProductRequest request);
	
	void deleteProduct(Long id);

}
