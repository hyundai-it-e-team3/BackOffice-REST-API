package com.mycompany.backOfficeAPI.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.productDB.ProductDetailDAO;
import com.mycompany.backOfficeAPI.dao.productDB.ProductImgDAO;
import com.mycompany.backOfficeAPI.dao.productDB.StockDAO;
import com.mycompany.backOfficeAPI.dto.product.ProductDetailDTO;
import com.mycompany.backOfficeAPI.dto.product.StockDTO;
import com.mycompany.backOfficeAPI.dto.product.StockDetailDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

	public List<StockDetailDTO> getProductDetailList(String searchType, String keyWord, int startRow, int endRow, String sortId) {
		Map<String,Object> map = new HashMap<>();
		map.put("searchType",searchType);
		map.put("keyWord",keyWord);
		map.put("startRow",startRow);
		map.put("endRow",endRow);
		map.put("sortId",sortId);
		
		log.info(sortId);
		List<ProductDetailDTO> list = productDetailDAO.selectProductDetailBySearch(map);
		
		
		List<StockDetailDTO> stockList = new ArrayList<>();
		for(ProductDetailDTO productDetailDTO: list) {
			productDetailDTO.setStockList(stockDAO.selectByProductDetailId(productDetailDTO.getProductDetailId()));
			
			for(StockDTO stock: productDetailDTO.getStockList()) {
				StockDetailDTO stockDetail = new StockDetailDTO();
				stockDetail.setProductId(productDetailDTO.getProductId());
				stockDetail.setProductDetailId(productDetailDTO.getProductDetailId());
				stockDetail.setBrandName(productDetailDTO.getBrandName());
				stockDetail.setName(productDetailDTO.getName());
				stockDetail.setPsize(stock.getPsize());
				stockDetail.setAmount(stock.getAmount());
				stockList.add(stockDetail);
			}
		}
		
		return stockList;
	}

	public int getTotalProductDetail(String searchType, String keyWord) {
		Map<String,String> map = new HashMap<>();
		map.put("searchType",searchType);
		map.put("keyWord",keyWord);
		return productDetailDAO.selectTotalProductDetailBySearch(map);
	}

}
