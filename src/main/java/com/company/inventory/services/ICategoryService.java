package com.company.inventory.services;

import org.springframework.http.ResponseEntity;

import com.company.inventory.response.CategoryResponseRest;

public interface ICategoryService {
	ResponseEntity<CategoryResponseRest> search();
}
