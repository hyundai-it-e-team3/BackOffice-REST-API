package com.mycompany.backOfficeAPI.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.productDB.BrandDAO;
import com.mycompany.backOfficeAPI.dao.productDB.CategoryDAO;
import com.mycompany.backOfficeAPI.dto.product.BrandCategoryDTO;
import com.mycompany.backOfficeAPI.dto.product.BrandCategoryTempDTO;
import com.mycompany.backOfficeAPI.dto.product.CategoryDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService {
	
	
	@Resource
	private CategoryDAO categoryDAO;
	
	@Resource
	private BrandDAO brandDAO;
	
	public List<CategoryDTO> getMainCategory(){
		return categoryDAO.selectAllMain();
	}

	public List<CategoryDTO> getMiddleCategory(String mainCategoryId) {
		return categoryDAO.selecMiddleCategoryById(mainCategoryId);
	}

	public List<CategoryDTO> getSubCategory(String parentCategoryId) {
		return categoryDAO.selectSubCategory(parentCategoryId);
	}

	public List<CategoryDTO> getCategory() {
		List<CategoryDTO> mainList = categoryDAO.selectAllMain();
		List<CategoryDTO> middleList = categoryDAO.selectAllMiddle();
		List<CategoryDTO> subList = categoryDAO.selectAllSub();
		
		
		int idx = 0;
		middleList.get(0).setCategoryList(new ArrayList<CategoryDTO>());
		for(int i = 0; i < subList.size(); i++) {
			if(!middleList.get(idx).getCategoryId().equals(subList.get(i).getParentCategoryId())) {
				idx++;
				middleList.get(idx).setCategoryList(new ArrayList<CategoryDTO>());
				middleList.get(idx).getCategoryList().add(subList.get(i));
			}else {
				middleList.get(idx).getCategoryList().add(subList.get(i));
			}
		}
		
		idx = 0;
		mainList.get(0).setCategoryList(new ArrayList<CategoryDTO>());
		for(int i = 0; i < middleList.size(); i++) {
			if(!mainList.get(idx).getCategoryId().equals(middleList.get(i).getParentCategoryId())) {
				idx++;
				mainList.get(idx).setCategoryList(new ArrayList<CategoryDTO>());
				mainList.get(idx).getCategoryList().add(middleList.get(i));
			}else {
				mainList.get(idx).getCategoryList().add(middleList.get(i));
			}
		}
		
		
		return mainList;
	}

	public List<BrandCategoryDTO> getBrandCategory() {
		List<BrandCategoryDTO> brandList = brandDAO.selectAllBrand();
		List<BrandCategoryTempDTO> brandMainList = brandDAO.selectMainBrand();
		List<BrandCategoryTempDTO> brandMiddleList = brandDAO.selectMiddleBrand();
		List<BrandCategoryTempDTO> brandSubList = brandDAO.selectSubBrand();
		int idx = 0;
		brandMiddleList.get(0).setCategoryList(new ArrayList<BrandCategoryTempDTO>());
		for(int i = 0; i < brandSubList.size(); i++) {
			
			if(!brandMiddleList.get(idx).getCategoryId().equals(brandSubList.get(i).getParentCategoryId())||!brandMiddleList.get(idx).getBrandName().equals(brandSubList.get(i).getBrandName())) {
				idx++;
				brandMiddleList.get(idx).setCategoryList(new ArrayList<BrandCategoryTempDTO>());
				brandMiddleList.get(idx).getCategoryList().add(brandSubList.get(i));
			}else {
				brandMiddleList.get(idx).getCategoryList().add(brandSubList.get(i));
			}
		}
		
		idx = 0;
		
		brandMainList.get(0).setCategoryList(new ArrayList<BrandCategoryTempDTO>());
		for(int i = 0; i < brandMiddleList.size(); i++) {
			if(!brandMainList.get(idx).getCategoryId().equals(brandMiddleList.get(i).getParentCategoryId())||!brandMainList.get(idx).getBrandName().equals(brandMiddleList.get(i).getBrandName())) {
				idx++;
				brandMainList.get(idx).setCategoryList(new ArrayList<BrandCategoryTempDTO>());
				brandMainList.get(idx).getCategoryList().add(brandMiddleList.get(i));
			}else {
				brandMainList.get(idx).getCategoryList().add(brandMiddleList.get(i));
			}
		}
		
		idx = 0;
		brandList.get(0).setBrandCategoryTempList(new ArrayList<BrandCategoryTempDTO>());
		for(int i = 0; i < brandMainList.size(); i++) {
			if(!brandMainList.get(i).getBrandName().equals(brandList.get(idx).getBrandName())) {
				idx++;
				brandList.get(idx).setBrandCategoryTempList(new ArrayList<BrandCategoryTempDTO>());
				brandList.get(idx).getBrandCategoryTempList().add(brandMainList.get(i));
			}else {
				brandList.get(idx).getBrandCategoryTempList().add(brandMainList.get(i));
			}
		}

		for(int i = 0; i < brandList.size(); i++) {
			brandList.get(i).setMdPickList(brandDAO.selectMdPickByBrand(brandList.get(i).getBrandName()));
		}
		return brandList;
	}
}
