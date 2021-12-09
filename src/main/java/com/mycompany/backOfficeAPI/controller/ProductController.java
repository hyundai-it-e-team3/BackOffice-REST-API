package com.mycompany.backOfficeAPI.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.product.PagerDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductSearchDTO;
import com.mycompany.backOfficeAPI.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {
	
	@Resource
	private ProductService productService;
	
	@RequestMapping("/{productId}")
	public ProductDTO getProduct(@PathVariable String productId){
		return productService.getProduct(productId);
	}
	
	@RequestMapping("/brandProductList/{brandName}/{categoryId}/{startRow}/{rowCount}/{sortId}")
	public List<ProductDTO> getBrandProductList(@PathVariable("brandName") String brandName,@PathVariable("startRow") int startRow,@PathVariable("rowCount") int rowCount,@PathVariable("sortId") int sortId,@PathVariable("categoryId") String categoryId){
		if(categoryId.equals("null"))
			categoryId = "";
		int totalRows = productService.getRowCount(brandName,categoryId);
		int endRow = startRow+rowCount;
		if(endRow>totalRows) {
			endRow = totalRows;
		}
		
		return productService.getBrandProductList(brandName,categoryId,startRow,endRow,sortId);  
	}
	
	@RequestMapping("/list/{categoryId}/{startRow}/{rowCount}/{sortId}")
	public List<ProductDTO> getCategoryProductList(@PathVariable("categoryId") String categoryId,@PathVariable("startRow") int startRow,@PathVariable("rowCount") int rowCount,@PathVariable("sortId") int sortId){
		if(categoryId.equals("null"))
			categoryId = "";
		int totalRows = productService.getRowCountByCategory(categoryId);
		log.info("categoryId "+categoryId);
		log.info(" "+totalRows);
		
		
		int endRow = startRow+rowCount;
		if(endRow>totalRows) {
			endRow = totalRows;
		}
		List <ProductDTO> list = productService.getCategoryProductList(categoryId,startRow,endRow, sortId);
		for(ProductDTO product : list) {
			log.info(product.getProductId());
		}
		return list;
	}
	
	@RequestMapping("/list/{idStr}")
	public List<ProductDTO> getWishProductList(@PathVariable String idStr){
		log.info(idStr);
		return productService.getProductListByWish(idStr);
	}
	
	@RequestMapping("/list/text/{text}/{startRow}/{rowCount}/{sortId}")
	public List<ProductDTO> getProductByText(@PathVariable String text,@PathVariable int startRow,@PathVariable int rowCount,@PathVariable int sortId){
		if(text.equals("0"))
			text = "";
		int totalRows = productService.getRowCountByText(text);
		
		int endRow = startRow+rowCount;
		if(endRow>totalRows) {
			endRow = totalRows;
		}
		
		return productService.getProductByText(text,startRow,endRow,sortId);
	}
}
