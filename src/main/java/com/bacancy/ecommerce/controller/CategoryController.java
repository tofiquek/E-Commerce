package com.bacancy.ecommerce.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bacancy.ecommerce.dto.CategoryDto;
import com.bacancy.ecommerce.dto.UserDto;
import com.bacancy.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/admin/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private HttpServletRequest request;
	
	Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> findAllCategories(){
		logger.info("findAllCategories method started & Request URI = "+ request.getRequestURI());
		List<CategoryDto> categories = categoryService.allCategory();
		logger.info("findAllCategories method Ended & Response Generated");
		return new ResponseEntity(categories, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> findCategory(@PathVariable(name = "id") Long id) {
		logger.info("findCategory method started & Request URI = "+ request.getRequestURI());
		ResponseEntity responseEntity = new ResponseEntity( categoryService.getCategoryById(id),HttpStatus.OK);
		logger.info("findCategory method Ended & Response Generated");
		return responseEntity;
	}
	
	@PostMapping("/admin/{userId}")
	public ResponseEntity<UserDto> saveCategory(@PathVariable(name = "userId") Long userId,@RequestBody CategoryDto categoryDto) {
		logger.info("saveCategory method started & Request URI = "+ request.getRequestURI());
		ResponseEntity responseEntity = new ResponseEntity(categoryService.addCategory(userId, categoryDto), HttpStatus.OK);
		logger.info("saveCategory method Ended & Response Generated");
		return responseEntity ;
		
	}
	
	@PutMapping("/admin/{userId}")
	public ResponseEntity<UserDto> updateCategory(@PathVariable(name = "userId") Long userId,@RequestBody CategoryDto categoryDto) {
		logger.info("updateCategory method started & Request URI = "+ request.getRequestURI());
		ResponseEntity responseEntity = new ResponseEntity(categoryService.updateCategory(userId, categoryDto), HttpStatus.OK);
		logger.info("updateCategory method Ended & Response Generated");
		return responseEntity ;
		
	}
	
	@DeleteMapping("/admin/{id}")
	public ResponseEntity deleteCategory(@PathVariable(name="id") Long id) {
		logger.info("deleteCategory method started & Request URI = "+ request.getRequestURI());
		categoryService.deleteCategory(id);
		logger.info("deleteCategory method Ended & Response Generated");
		return new ResponseEntity(HttpStatus.OK); 
	}
}
