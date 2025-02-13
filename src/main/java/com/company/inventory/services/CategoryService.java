package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;

@Service
public class CategoryService implements ICategoryService{

	@Autowired
	private ICategoryDao categoryDao;
	
	@Override
	@Transactional(readOnly=true)
	public ResponseEntity<CategoryResponseRest> search() {
		CategoryResponseRest response = new CategoryResponseRest();
		
		try {
			List<Category> list = (List<Category>) categoryDao.findAll();
			
			response.getCategoryResponse().setCategory(list);
			response.setMetadata("OK", "0", "Consulta exitosa");
		}
		catch(Exception ex) {
			response.setMetadata("ERR", "-1", "Consulta sin éxito");
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}
	
	@Override
	@Transactional(readOnly=true)
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		
		try {
			Optional<Category> result = categoryDao.findById(id);
			
			if(result.isPresent()) {
				list.add(result.get());
				response.getCategoryResponse().setCategory(list);
				response.setMetadata("OK", "0", "Consulta exitosa");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
			}			
			
			response.setMetadata("KO", "1", "Categoría no encontrada");
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
			
			
		}catch(Exception ex) {
			response.setMetadata("ERR", "-1", "Consulta sin éxito");
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}
}
