package com.company.inventory.services;

import org.springframework.http.ResponseEntity;

import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;

public interface IProductService {
	ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
	ResponseEntity<ProductResponseRest> findById(Long id);
}
