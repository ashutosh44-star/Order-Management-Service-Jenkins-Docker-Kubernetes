package com.ashu.ordermanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.ashu.ordermanagement.dto.ProductRequest;
import com.ashu.ordermanagement.dto.ProductResponse;
import com.ashu.ordermanagement.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
	@PostMapping("/create")
	public ProductResponse createProduct(
	        @Valid @RequestBody ProductRequest request) {

	    return productService.createProduct(request);
	}
	@GetMapping("/getAllProducts")
	public List<ProductResponse> getAllProducts() {
	    return productService.getAllProducts();
	}
	@GetMapping("getProductById/{id}")
	public ProductResponse getProductById(@PathVariable Long id) {
		return productService.getProductById(id);
	}
	@PutMapping("updateProduct/{id}")
	public ProductResponse updateProduct(
	        @PathVariable Long id,
	        @Valid @RequestBody ProductRequest request) {

	    return productService.updateProduct(id, request);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable Long id) {
	    productService.deleteProduct(id);
	}
}