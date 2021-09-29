package com.bacancy.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bacancy.ecommerce.dto.CategoryDto;
import com.bacancy.ecommerce.dto.ProductDto;
import com.bacancy.ecommerce.dto.UserDto;
import com.bacancy.ecommerce.entity.Product;
import com.bacancy.ecommerce.exception.NotAccessAbleException;
import com.bacancy.ecommerce.exception.ProductNotFoundException;
import com.bacancy.ecommerce.repository.ProductRepository;
import com.bacancy.ecommerce.service.CategoryService;
import com.bacancy.ecommerce.service.ProductService;
import com.bacancy.ecommerce.service.UserService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ModelMapper modelMapper;
	
	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Override
	public ProductDto addProduct(Long userId,Long categoryId,ProductDto productDto) {
		logger.info("addProduct Method Started");
		UserDto userDto = userService.getUserById(userId);
		if(userDto.getRoleId()!=0) {
			logger.error("Client does not have permission to add product user id = "+userDto.getId());
			throw new NotAccessAbleException("Client does not have permission to add Product");
		}
		CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
		productDto.setUser(userDto);
		productDto.setCategory(categoryDto);
		Product product = modelMapper.map(productDto, Product.class);
		Product savedProduct= productRepository.save(product);
		ProductDto savedProductDto = modelMapper.map(savedProduct, ProductDto.class);
		logger.info("addProduct Method Ended");
		return savedProductDto;
		
	}
	
	@Override
	public ProductDto updateProduct(ProductDto productDto) {
		logger.info("updateProduct Method Started");
		Product product = modelMapper.map(productDto, Product.class);
		Product updatedProduct= productRepository.save(product);
		ProductDto updatedProductDto = modelMapper.map(updatedProduct, ProductDto.class);
		logger.info("updateProduct Method Ended");
		return updatedProductDto;
		
	}

	@Override
	public ProductDto getProductById(Long id) {
		logger.info("getProductById Method Started");
		Optional<Product> productOptional = productRepository.findById(id);
		if(!productOptional.isPresent()) {
			logger.error("Product with id "+id +" not found");
			throw new ProductNotFoundException("Product not found by id = "+id);
		}
		ProductDto	productDto = modelMapper.map(productOptional.get(), ProductDto.class);
		logger.info("getProductById Method Ended");
		return productDto;
	}

	@Override
	public List<ProductDto> allProducts() {
		logger.info("allProducts Method Started");
		List<Product> products= productRepository.findAll(); 
		List<ProductDto> productsDto = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
		logger.info("allProducts Method Ended");
		return productsDto;
	}

	@Override
	public void deleteProduct(Long id) {
		logger.info("deleteProduct Method Started");
		productRepository.deleteById(id);
		logger.info("deleteProduct Method Ended");
	}

}
