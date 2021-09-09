package com.bacancy.ecommerce.controller;

import java.util.List;

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
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<CategoryDto>> findAllCategories(){
		List<CategoryDto> categories = categoryService.allCategory();
		return new ResponseEntity(categories, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> findCategory(@PathVariable(name = "id") Long id) {
		return new ResponseEntity( categoryService.getCategoryById(id),HttpStatus.OK);
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<UserDto> saveCategory(@PathVariable(name = "userId") Long userId,@RequestBody CategoryDto categoryDto) {
		return new ResponseEntity(categoryService.addCategory(userId, categoryDto), HttpStatus.OK) ;
		
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateCategory(@PathVariable(name = "userId") Long userId,@RequestBody CategoryDto categoryDto) {
		return new ResponseEntity(categoryService.updateCategory(userId, categoryDto), HttpStatus.OK) ;
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteCategory(@PathVariable(name="id") Long id) {
		categoryService.deleteCategory(id);
		return new ResponseEntity(HttpStatus.OK); 
	}
}
