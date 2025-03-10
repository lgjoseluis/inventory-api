package com.company.inventory.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;

class CategoryRestControllerTest {
	@Mock
	private ICategoryService service;
	
	@InjectMocks
	CategoryRestController categoryController;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void addTest() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		Category category = new Category();
		
		category.setId(Long.valueOf(1));
		category.setName("Lácteos");
		category.setDescription("Productos derivados de la leche");
		
		when(service.save(any(Category.class)))
			.thenReturn(
						new ResponseEntity<CategoryResponseRest>(HttpStatus.OK)
					);
		
		ResponseEntity<CategoryResponseRest> response = categoryController.add(category);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}	
}
