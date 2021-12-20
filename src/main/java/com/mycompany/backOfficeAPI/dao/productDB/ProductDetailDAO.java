package com.mycompany.backOfficeAPI.dao.productDB;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.product.ProductDetailDTO;

@Mapper
public interface ProductDetailDAO {

	public ProductDetailDTO selectByProductDetailId(String productDetailId);

	public List<ProductDetailDTO> selectAllByProductId(String productId);

	public int selectPrice(String productDetailId);

	public int selectTotalProductDetailBySearch(Map<String, String> map);

	public List<ProductDetailDTO> selectProductDetailBySearch(Map<String, Object> map);

	public void insertProductDetail(ProductDetailDTO productDetailDTO);

	public int selectTotalProductDetailById(String productDetailId);

	public List<ProductDetailDTO> selectProductDetailListById(Map<String, Object> map);

}
