package com.company.inventory.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.services.IProductService;
import com.company.inventory.util.Util;

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
}
