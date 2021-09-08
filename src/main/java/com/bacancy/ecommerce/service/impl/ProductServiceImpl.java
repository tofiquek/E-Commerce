package com.bacancy.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
	
	@Override
	public ProductDto addProduct(Long userId,Long categoryId,ProductDto productDto) {
		UserDto userDto = userService.getUserById(userId);
		if(userDto.getRoleId()!=0) {
			throw new NotAccessAbleException("Client does not have permission to add Product");
		}
		CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
		productDto.setUserDto(userDto);
		productDto.setCategoryDto(categoryDto);
		Product product = modelMapper.map(productDto, Product.class);
		Product savedProduct= productRepository.save(product);
		ProductDto savedProductDto = modelMapper.map(savedProduct, ProductDto.class);
		return savedProductDto;
		
	}

	@Override
	public ProductDto getProductById(Long id) {
		Optional<Product> productOptional = productRepository.findById(id);
		if(!productOptional.isPresent()) {
			throw new ProductNotFoundException("Product not found by id = "+id);
		}
		ProductDto	productDto = modelMapper.map(productOptional.get(), ProductDto.class);
		return productDto;
	}

	@Override
	public List<ProductDto> allProducts() {
		List<Product> products= productRepository.findAll(); 
		List<ProductDto> productsDto = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
		return productsDto;
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

}
