package com.bacancy.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bacancy.ecommerce.dto.CategoryDto;
import com.bacancy.ecommerce.dto.UserDto;
import com.bacancy.ecommerce.entity.Category;
import com.bacancy.ecommerce.repository.CategoryRepository;
import com.bacancy.ecommerce.service.CategoryService;
import com.bacancy.ecommerce.service.UserService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto addCategory(Long userId,CategoryDto categoryDto) {
		UserDto userDto = userService.getUserById(userId);
		if(userDto.getRoleId()!=0) {
			throw new RuntimeException();
		}
		categoryDto.setUser(userDto);
		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		CategoryDto savedCategoryDto = modelMapper.map(savedCategory, CategoryDto.class);
		return savedCategoryDto;
	}

	@Override
	public CategoryDto getCategoryById(Long id) {
		Optional<Category> categoryOptional = categoryRepository.findById(id);
		CategoryDto categoryDto = null;
		if (categoryOptional.isPresent()) {
			
			categoryDto = modelMapper.map(categoryOptional.get(), CategoryDto.class);
		}
		return categoryDto;
	}

	@Override
	public List<CategoryDto> allCategory() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDto> categoriesDto = categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoriesDto;
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}

}
