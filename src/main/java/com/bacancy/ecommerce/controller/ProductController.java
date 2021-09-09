package com.bacancy.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bacancy.ecommerce.dto.ProductDto;
import com.bacancy.ecommerce.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<ProductDto>> findAllProducts(){
		List<ProductDto> products = productService.allProducts();
		return new ResponseEntity(products, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> findProduct(@PathVariable(name = "id") Long id) {
		return new ResponseEntity( productService.getProductById(id),HttpStatus.OK);
	}
	
	@PostMapping("/{userId}/{categoryId}")
	public ResponseEntity<ProductDto> saveProduct(@PathVariable(name = "userId") Long userId,@PathVariable(name = "categoryId") Long categoryId,@RequestBody ProductDto productDto) {
		return new ResponseEntity(productService.addProduct(userId, categoryId, productDto), HttpStatus.OK) ;
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteProduct(@PathVariable(name="id") Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity(HttpStatus.OK); 
	}
}
