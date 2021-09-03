package com.bacancy.ecommerce.service;

import java.util.List;

import com.bacancy.ecommerce.dto.ProductDto;

public interface ProductService {
	ProductDto addProduct(Long userId,Long categoryId,ProductDto productDto);
	
	ProductDto getProductById(Long id);
	
	List<ProductDto> allProducts();
	
	void deleteProduct(Long id);
}
