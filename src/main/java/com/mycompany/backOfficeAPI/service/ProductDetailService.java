package com.mycompany.backOfficeAPI.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.productDB.ProductDetailDAO;
import com.mycompany.backOfficeAPI.dao.productDB.ProductImgDAO;
import com.mycompany.backOfficeAPI.dao.productDB.StockDAO;
import com.mycompany.backOfficeAPI.dto.product.ProductDetailDTO;

@Service
public class ProductDetailService {

	@Resource
	private ProductDetailDAO productDetailDAO;
	
	@Resource
	private StockDAO stockDAO;
	
	@Resource
	private ProductImgDAO productImgDAO;
	
	public ProductDetailDTO getProductDetail(String productDetailId) {
		ProductDetailDTO productDetailDTO = productDetailDAO.selectByProductDetailId(productDetailId);
		productDetailDTO.setStockList(stockDAO.selectByProductDetailId(productDetailId));
		productDetailDTO.setImgList(productImgDAO.selectByProductDetailId(productDetailId));
		
		return productDetailDTO;
	}
	
	public int getPrice(String productDetailId) {
		return productDetailDAO.selectPrice(productDetailId);
	}

}
