package com.mycompany.backOfficeAPI.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.product.BrandCategoryDTO;
import com.mycompany.backOfficeAPI.dto.product.CategoryDTO;
import com.mycompany.backOfficeAPI.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

	@Resource
	private CategoryService categoryService;
	
	@RequestMapping("")
	public List<CategoryDTO> getAllCategory() {
		log.info("aaaaaa");
		return categoryService.getCategory();
	}
	
	@RequestMapping("/brand")
	public List<BrandCategoryDTO> getAllBrandCategory() {
		log.info("aaaaaa");
		return categoryService.getBrandCategory();
	}
	
	
	@RequestMapping("/{mainCategoryId}")
	public List<CategoryDTO> getMiddleCategory(@PathVariable String mainCategoryId){
		return categoryService.getMiddleCategory(mainCategoryId);
	}
	
	@RequestMapping("/{mainCategoryId}/{middleCategory}")
	public List<CategoryDTO> getSubCategory(@PathVariable("mainCategoryId") String mainCategoryId, @PathVariable("middleCategory") String middleCategoryId){
		
		String parentCategoryId = mainCategoryId+middleCategoryId;
		return categoryService.getSubCategory(parentCategoryId);
	}
}
