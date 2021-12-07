package com.mycompany.backOfficeAPI.dao.productDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.product.ProductImgDTO;

@Mapper
public interface ProductImgDAO {

	List<ProductImgDTO> selectByProductDetailId(String productDetailId);

}
