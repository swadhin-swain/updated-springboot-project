package com.swadhin.bolg.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swadhin.bolg.entities.Category;
import com.swadhin.bolg.exceptions.ResourseNotFoundException;
import com.swadhin.bolg.payloads.CategoryDto;
import com.swadhin.bolg.repository.CategoryRepo;
import com.swadhin.bolg.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		
		Category addedCat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourseNotFoundException("Category", "categoryId", categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDesc(categoryDto.getCategoryDesc());
		
		Category updatedCat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourseNotFoundException("Category", "categoryId", categoryId));
		
		this.categoryRepo.delete(cat);
		
	}

	@Override
	public CategoryDto getCategory(int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourseNotFoundException("Category", "categoryId", categoryId));
		
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = new ArrayList<>();
		
		categories = this.categoryRepo.findAll();
		
		List<CategoryDto> categoryDtos = categories.stream().map(category->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		
		return categoryDtos;
	}

}
