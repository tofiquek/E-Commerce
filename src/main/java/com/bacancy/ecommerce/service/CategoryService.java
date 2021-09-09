package com.bacancy.ecommerce.service;

import java.util.List;

import com.bacancy.ecommerce.dto.CategoryDto;

public interface CategoryService {
	
	CategoryDto addCategory(Long userId,CategoryDto categoryDto);
	
	CategoryDto updateCategory(Long userId,CategoryDto categoryDto);
	
	CategoryDto getCategoryById(Long id);
	
	List<CategoryDto> allCategory();
	
	void deleteCategory(Long id);

}
