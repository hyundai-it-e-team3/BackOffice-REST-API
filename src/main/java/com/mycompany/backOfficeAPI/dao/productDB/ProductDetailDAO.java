package com.mycompany.backOfficeAPI.dao.productDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.product.ProductDetailDTO;

@Mapper
public interface ProductDetailDAO {

	public ProductDetailDTO selectByProductDetailId(String productDetailId);

	public List<ProductDetailDTO> selectAllByProductId(String productId);

	public int selectPrice(String productDetailId);

}
