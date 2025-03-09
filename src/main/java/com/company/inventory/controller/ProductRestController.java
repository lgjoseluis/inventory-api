package com.company.inventory.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.inventory.model.Product;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.services.IProductService;
import com.company.inventory.util.ExportCategoryExcel;
import com.company.inventory.util.ExportProductExcel;
import com.company.inventory.util.Util;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {
	@Autowired
	private IProductService service;
	
	/**
	 * Add product
	 * @param picture
	 * @param name
	 * @param price
	 * @param account
	 * @param categoryId
	 * @return
	 */
	@PostMapping
	public ResponseEntity<ProductResponseRest> add(
				@RequestParam MultipartFile picture,
				@RequestParam String name,
				@RequestParam float price,
				@RequestParam int account,
				@RequestParam Long categoryId
			){
		Product product = new Product();
		
		product.setName(name);
		product.setPrice(price);
		product.setAccount(account);		
		
		try {
			product.setPicture(Util.compressZLib(picture.getBytes()));
		} catch (IOException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
		
		ResponseEntity<ProductResponseRest> response = service.save(product, categoryId);
		
		return response;
	}
	
	/**
	 * Get by ID
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseRest> findById(@PathVariable Long id){
		ResponseEntity<ProductResponseRest> response = service.findById(id);
		
		return response;
	}
	
	/**
	 * Search by Name
	 * @param name
	 * @return
	 */
	@GetMapping("/search")
	public ResponseEntity<ProductResponseRest> findByName(@RequestParam String name){
		ResponseEntity<ProductResponseRest> response = service.findByName(name);
		
		return response;
	}
	
	/**
	 * Delete by ID
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<ProductResponseRest> deleteById(@PathVariable Long id){
		ResponseEntity<ProductResponseRest> response = service.deleteById(id);
		
		return response;
	}
	
	/**
	 * Find all
	 * @return
	 */
	@GetMapping
	public ResponseEntity<ProductResponseRest> findAll(){
		ResponseEntity<ProductResponseRest> response = service.findAll();
		
		return response;
	}
	
	/**
	 * Update product
	 * @param picture
	 * @param name
	 * @param price
	 * @param account
	 * @param id
	 * @param categoryId
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponseRest> update(
			@RequestParam MultipartFile picture,
			@RequestParam String name,
			@RequestParam float price,
			@RequestParam int account,			
			@RequestParam Long categoryId,
			@PathVariable Long id
		){
		Product product = new Product();
		
		product.setName(name);
		product.setPrice(price);
		product.setAccount(account);		
		
		try {
			product.setPicture(Util.compressZLib(picture.getBytes()));
		} catch (IOException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
		
		ResponseEntity<ProductResponseRest> response = service.update(product, id, categoryId);
		
		return response;
	}
	
	/***
	 * Export to excel
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException{
		ExportProductExcel exportExcel;
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=result_products.xlsx";
		
		ResponseEntity<ProductResponseRest> result = service.findAll();
		
		response.setContentType("application/octec-stream");
		response.setHeader(headerKey, headerValue);
		
		exportExcel = new ExportProductExcel(
				result.getBody().getProductResponse().getProducts()
				);
		
		exportExcel.export(response);
	} 
}
