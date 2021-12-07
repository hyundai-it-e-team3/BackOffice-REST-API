package com.mycompany.backOfficeAPI.dao.productDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.product.CategoryDTO;

@Mapper
public interface CategoryDAO {

	public List<CategoryDTO> selectAllMain();
	
	public List<CategoryDTO> selectAllMiddle();
	
	public List<CategoryDTO> selectAllSub();

	public List<CategoryDTO> selecMiddleCategoryById(String mainCategoryId);

	public List<CategoryDTO> selectSubCategory(String parentCategoryId);

	public List<CategoryDTO> selectAllCategory();

}
