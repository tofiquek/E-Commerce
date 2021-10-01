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

	@Autowired
	private HttpServletRequest request;
	
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> findAllProducts(){
		logger.info("findAllProducts method started & Request URI = "+ request.getRequestURI());
		
		List<ProductDto> products = productService.allProducts();
		ResponseEntity responseEntity = new ResponseEntity(products, HttpStatus.OK);
		logger.info("findAllProducts method Ended & Response Generated");
		return responseEntity;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> findProduct(@PathVariable(name = "id") Long id) {
		logger.info("findProduct method started & Request URI = "+ request.getRequestURI());
		ResponseEntity responseEntity = new ResponseEntity( productService.getProductById(id),HttpStatus.OK);
		logger.info("findProduct method Ended & Response Generated");
		return responseEntity;
	}
	
	@PostMapping("/{userId}/{categoryId}")
	public ResponseEntity<ProductDto> saveProduct(@PathVariable(name = "userId") Long userId,@PathVariable(name = "categoryId") Long categoryId,@RequestBody ProductDto productDto) {
		logger.info("findAllProducts method started & Request URI = "+ request.getRequestURI());
		
		ResponseEntity responseEntity = new ResponseEntity(productService.addProduct(userId, categoryId, productDto), HttpStatus.OK);
		logger.info("findAllProducts method ended & Response Generated");
		return responseEntity ;
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteProduct(@PathVariable(name="id") Long id) {
		logger.info("deleteProduct method started & Request URI = "+ request.getRequestURI());
		productService.deleteProduct(id);
		logger.info("deleteProduct method ended");
		return new ResponseEntity(HttpStatus.OK); 
	}
}
