package com.mycompany.backOfficeAPI.dao.productDB;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.backOfficeAPI.dto.product.ColorDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductSearchDTO;

@Mapper
public interface ProductDAO {

	public ProductDTO selectByProductId(String productId);

	public int selectAllProductByBrandName(String brandName);

	public int selectCountByBrandName(Map<String,String> map);
	
	public int selectCountByCategoryId(String categoryId);
	
	public int selectCountByText(String text);
	
	public int selectAllCount();
	
	

	public List<ProductDTO> selectProductListByBrandName(Map<String, Object> mp);

	public List<ProductDTO> selectAllProductList(Map<String, Object> mp);
	
	public List<ProductDTO> selectProductListByCategoryId(Map<String, Object> mp);
	
	public List<ProductDTO> selectProductListByWish(List<String> list);

	public int updateHitCount(String productId);

	public List<ProductDTO> SelectProductByText(Map<String, Object> mp);

	public List<ProductDTO> selectProductListBySearch(ProductSearchDTO productSearchDTO);

	public int selectProductListCountBySearch(ProductSearchDTO productSearchDTO);

	public List<ColorDTO> selectAllColor();

	public void insertProduct(ProductDTO productDTO);

	public void insertMdProduct(ProductDTO productDTO);

	public void updateMdStatus(ProductDTO productDTO);

	public void deleteMdProduct(ProductDTO productDTO);

	public void updateStatus(String productId);


	public void updateStatus(@Param(value = "productId") String productId,@Param(value = "status")int status);

	public int selectStatusById(String productId);

}
