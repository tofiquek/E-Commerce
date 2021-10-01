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
import com.bacancy.ecommerce.dto.UserDto;
import com.bacancy.ecommerce.entity.Category;
import com.bacancy.ecommerce.exception.CategoryNotFoundException;
import com.bacancy.ecommerce.exception.NotAccessAbleException;
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
	
	Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Override
	public CategoryDto addCategory(Long userId,CategoryDto categoryDto) {
		logger.info("addCategory Method Started");
		UserDto userDto = userService.getUserById(userId);
		if(userDto.getRoleId()!=0) {
			throw new NotAccessAbleException("Client has not Access to Add Category");
		}
		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		CategoryDto savedCategoryDto = modelMapper.map(savedCategory, CategoryDto.class);
		logger.info("addCategory Method Ended");
		return savedCategoryDto;
	}

	@Override
	public CategoryDto getCategoryById(Long id) {
		logger.info("getCategoryById Method Started");
		Optional<Category> categoryOptional = categoryRepository.findById(id);
		if(!categoryOptional.isPresent()) {
			throw new CategoryNotFoundException("Category Not found by id "+id);
		}
		CategoryDto	categoryDto = modelMapper.map(categoryOptional.get(), CategoryDto.class);
		logger.info("getCategory Method Ended");
		return categoryDto;
	}

	@Override
	public List<CategoryDto> allCategory() {
		logger.info("allCategory Method Started");
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDto> categoriesDto = categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		logger.info("allCategory Method Ended");
		return categoriesDto;
	}

	@Override
	public void deleteCategory(Long id) {
		logger.info("deleteCategory Method Started");
		categoryRepository.deleteById(id);
		logger.info("deleteCategory Method Ended");
	}

	@Override
	public CategoryDto updateCategory(Long userId, CategoryDto categoryDto) {
		logger.info("updateCategory Method Started");
		UserDto userDto = userService.getUserById(userId);
		if(userDto.getRoleId()!=0) {
			logger.error("Client has not Access to Update Category");
			throw new NotAccessAbleException("Client has not Access to Update Category");
		}
		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		CategoryDto savedCategoryDto = modelMapper.map(savedCategory, CategoryDto.class);
		logger.info("updateCategory Method Ended");
		return savedCategoryDto;
	}

}
