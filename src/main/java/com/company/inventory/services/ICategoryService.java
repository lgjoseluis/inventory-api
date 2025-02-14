package com.company.inventory.services;

import org.springframework.http.ResponseEntity;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;

public interface ICategoryService {
	ResponseEntity<CategoryResponseRest> search();
	ResponseEntity<CategoryResponseRest> searchById(Long id);
	ResponseEntity<CategoryResponseRest> save(Category category);
	ResponseEntity<CategoryResponseRest> update(Category category, Long id);
}
