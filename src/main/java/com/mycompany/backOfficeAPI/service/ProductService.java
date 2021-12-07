package com.mycompany.backOfficeAPI.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.productDB.ProductDAO;
import com.mycompany.backOfficeAPI.dao.productDB.ProductDetailDAO;
import com.mycompany.backOfficeAPI.dao.productDB.ProductImgDAO;
import com.mycompany.backOfficeAPI.dao.productDB.StockDAO;
import com.mycompany.backOfficeAPI.dto.product.ProductDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductDetailDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {
	
	@Resource
	private ProductDAO productDAO;
	
	@Resource
	private ProductDetailDAO productDetailDAO;
	
	@Resource
	private StockDAO stockDAO;
	
	@Resource
	private ProductImgDAO productImgDAO;
	
	public ProductDTO getProduct(String productId) {
		
		productDAO.updateHitCount(productId);
		
		ProductDTO productDTO = productDAO.selectByProductId(productId);
		log.info(productId);
		productDTO.setProductDetailList(productDetailDAO.selectAllByProductId(productId));
		
		for(ProductDetailDTO productDetail: productDTO.getProductDetailList()) {
			productDetail.setStockList(stockDAO.selectByProductDetailId(productDetail.getProductDetailId()));
			productDetail.setImgList(productImgDAO.selectByProductDetailId(productDetail.getProductDetailId()));
			productDetail.setWithImgList(productImgDAO.selectByProductDetailId(productDetail.getWithProduct()));
		}		
		return productDTO;
	}

	public int getRowCount(String brandName, String categoryId) {
		Map<String,String> mp = new HashMap<>();
		mp.put("brandName",brandName);
		mp.put("categoryId",categoryId);
		return productDAO.selectCountByBrandName(mp);
	}

	public List<ProductDTO> getBrandProductList(String brandName,String categoryId ,int startRow, int endRow, int sortId) {
		Map<String,Object> mp = new HashMap<>();
		mp.put("brandName",brandName);
		mp.put("startRow",startRow);
		mp.put("endRow",endRow);
		mp.put("categoryId",categoryId);
		
		if(sortId==0) {
			mp.put("sortId","reg_date");
			mp.put("sortWay","asc");
		}else if(sortId==1){
			mp.put("sortId","price");
			mp.put("sortWay","asc");
		}else if(sortId==2){
			mp.put("sortId","price");
			mp.put("sortWay","desc");
		}
		return productDAO.selectProductListByBrandName(mp);
	
	}

	public int getRowCountByCategory(String categoryId) {	
		if(categoryId.equals("0"))
			return productDAO.selectAllCount();
		return productDAO.selectCountByCategoryId(categoryId);
	}

	public List<ProductDTO> getCategoryProductList(String categoryId, int startRow, int endRow, int sortId) {
		
		Map<String,Object> mp = new HashMap<>();
		
		mp.put("categoryId",categoryId);
		mp.put("startRow",startRow);
		mp.put("endRow",endRow);
		
		if(sortId==0) {
			mp.put("sortId","reg_date");
			mp.put("sortWay","desc");
		}else if(sortId==1){
			mp.put("sortId","price");
			mp.put("sortWay","asc");
		}else if(sortId==2){
			mp.put("sortId","price");
			mp.put("sortWay","desc");
		}else{
			mp.put("sortId","hit_count");
			mp.put("sortWay","desc");
		}
		
		
		log.info(categoryId+" "+startRow+" "+endRow+" "+sortId);
		if(categoryId.equals("0"))
			return productDAO.selectAllProductList(mp);
		return productDAO.selectProductListByCategoryId(mp);
	}
	
	public List<ProductDTO> getProductListByWish(String idStr) {
		List<String> list = Arrays.asList(idStr.split(","));
		log.info(list.toString());
		return productDAO.selectProductListByWish(list);
	}

	public List<ProductDTO> getProductByText(String text,int startRow, int endRow, int sortId) {
		Map<String,Object> mp = new HashMap<>();		
		mp.put("text",text);
		mp.put("startRow",startRow);
		mp.put("endRow",endRow);
		
		if(sortId==0) {
			mp.put("sortId","reg_date");
			mp.put("sortWay","desc");
		}else if(sortId==1){
			mp.put("sortId","price");
			mp.put("sortWay","asc");
		}else if(sortId==2){
			mp.put("sortId","price");
			mp.put("sortWay","desc");
		}else{
			mp.put("sortId","hit_count");
			mp.put("sortWay","desc");
		}
		
		
		log.info(text+" "+startRow+" "+endRow+" "+sortId);
		return productDAO.SelectProductByText(mp);
	}

	public int getRowCountByText(String text) {
		return productDAO.selectCountByText(text);
	}

	public ProductDTO getCartProductDetail(String productDetailId) {
		String productId = productDetailId.substring(0, productDetailId.length()-3);
		ProductDTO productDTO = productDAO.selectByProductId(productId);
		productDTO.setProductDetail(productDetailDAO.selectByProductDetailId(productDetailId));
		productDTO.getProductDetail().setStockList(stockDAO.selectByProductDetailId(productDTO.getProductDetail().getProductDetailId()));
		productDTO.getProductDetail().setImgList(productImgDAO.selectByProductDetailId(productDTO.getProductDetail().getProductDetailId()));
		productDTO.getProductDetail().setWithImgList(productImgDAO.selectByProductDetailId(productDTO.getProductDetail().getWithProduct()));
		return productDTO;
	}
}
