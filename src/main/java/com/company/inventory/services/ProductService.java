package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.dao.IProductDao;
import com.company.inventory.model.Category;
import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.util.Util;

@Service
public class ProductService implements IProductService {
	private ICategoryDao categoryDao;
	private IProductDao productDao;
	
	ProductService(ICategoryDao categoryDao, IProductDao productDao){
		super();
		this.categoryDao = categoryDao;
		this.productDao = productDao;
	}

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> products = new ArrayList<>();
		
		try {
			Optional<Category> category = categoryDao.findById(categoryId);
			
			if(category.isEmpty()) {
				response.setMetadata("KO", "1", "Categoría de producto no encontrada");
				
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			product.setCategory(category.get());
			
			Product productSave = productDao.save(product);
			
			products.add(productSave);
			response.getProductResponse().setProducts(products);
			response.setMetadata("OK", "0", "Datos guardados con éxito");
			
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);			
		} catch(IllegalArgumentException e) {
			response.setMetadata("KO", "1", "Producto no guardado, revisar los datos");
			
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response.setMetadata("ERR", "-1", "No se puede gardar el producto");
			
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public ResponseEntity<ProductResponseRest> findById(Long id) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> products = new ArrayList<>();
		
		try {
			Optional<Product> product = productDao.findById(id);
			
			if(product.isEmpty()) {
				response.setMetadata("KO", "1", "Producto no encontrado");
				
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			byte[] picture = Util.decompressZLib(product.get().getPicture());
			
			product.get().setPicture(picture);
			
			products.add(product.get());
			
			response.setMetadata("OK", "0", "Consulta exitosa");
			response.getProductResponse().setProducts(products);
			
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);			
		} catch (Exception e) {
			response.setMetadata("ERR", "-1", "No se puede gardar el producto");
			
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
