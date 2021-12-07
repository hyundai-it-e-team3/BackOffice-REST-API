package com.mycompany.backOfficeAPI.dao.productDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.product.BrandCategoryDTO;
import com.mycompany.backOfficeAPI.dto.product.BrandCategoryTempDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductDTO;

@Mapper
public interface BrandDAO {

	List<ProductDTO> selectMdPickByBrand(String brandName);
	
	List<BrandCategoryDTO> selectAllBrand();

	List<BrandCategoryTempDTO> selectMainBrand();

	List<BrandCategoryTempDTO> selectMiddleBrand();

	List<BrandCategoryTempDTO> selectSubBrand();

}
