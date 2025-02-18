package com.company.inventory.services;

import org.springframework.http.ResponseEntity;

import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;

public interface IProductService {
	ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
	ResponseEntity<ProductResponseRest> findById(Long id);
	ResponseEntity<ProductResponseRest> findByName(String name);
	ResponseEntity<ProductResponseRest> deleteById(Long id);
	ResponseEntity<ProductResponseRest> findAll();
	ResponseEntity<ProductResponseRest> update(Product product, Long id, Long categoryId);
}
