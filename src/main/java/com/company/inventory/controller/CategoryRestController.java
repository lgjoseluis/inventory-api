package com.company.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/category")
public class CategoryRestController {
	@Autowired
	private ICategoryService service;
	
	/***
	 * Get all categories
	 * @return
	 */
	@GetMapping
	public ResponseEntity<CategoryResponseRest> search(){
		ResponseEntity<CategoryResponseRest> response = service.search();
		
		return response;
	}
	
	/***
	 * Get category by ID
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponseRest> searchById(@PathVariable Long id){
		ResponseEntity<CategoryResponseRest> response = service.searchById(id);
		
		return response;
	}
	
	/***
	 * Save new category
	 * @param category
	 * @return
	 */
	@PostMapping
	public ResponseEntity<CategoryResponseRest> add(@RequestBody Category category){
		ResponseEntity<CategoryResponseRest> response = service.save(category);
		
		return response;
	}
	
	/***
	 * Update category
	 * @param category
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<CategoryResponseRest> update(@RequestBody Category category, @PathVariable Long id){
		ResponseEntity<CategoryResponseRest> response = service.update(category, id);
		
		return response;
	}
	
	/***
	 * Delete category
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<CategoryResponseRest> delete(@PathVariable Long id){
		ResponseEntity<CategoryResponseRest> response = service.deleteById(id);
		
		return response;
	}
}