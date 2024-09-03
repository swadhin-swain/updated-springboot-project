package com.swadhin.bolg.services;

import java.util.List;

import com.swadhin.bolg.payloads.CategoryDto;

public interface CategoryService {

	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);
	
	//delete
	void deleteCategory(int categoryId);
	
	//get
	CategoryDto getCategory(int categoryId);
	
	//getAll
	List<CategoryDto> getAllCategory();
}
