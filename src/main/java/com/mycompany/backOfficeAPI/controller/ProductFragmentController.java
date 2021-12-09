package com.mycompany.backOfficeAPI.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.backOfficeAPI.dto.product.PagerDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductDetailDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductSearchDTO;
import com.mycompany.backOfficeAPI.dto.product.StockDetailDTO;
import com.mycompany.backOfficeAPI.service.ProductDetailService;
import com.mycompany.backOfficeAPI.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/fragment")
public class ProductFragmentController {
	
	@Resource
	private ProductService productService;
	
	@Resource
	private ProductDetailService productDetailService;
	
	
	@GetMapping("/stockList")
	public String getStockList(@RequestParam String searchType, @RequestParam String keyWord, @RequestParam int pageNo, Model model) {
		int totalRows = productDetailService.getTotalProductDetail(searchType,keyWord);
		PagerDTO pagerDTO = new PagerDTO(10, 5, totalRows, pageNo);
		List<StockDetailDTO> list = productDetailService.getProductDetailList(searchType,keyWord,pagerDTO.getStartRowNo(),pagerDTO.getEndRowNo());
		model.addAttribute("pager", pagerDTO);
		model.addAttribute("stockList", list);
		return "/product/stockListFragment";
	}
	
	
	@RequestMapping("/productList")
	public String getProductList(@RequestParam Map<String,String> map,Model model){
		
		ProductSearchDTO productSearchDTO = new ProductSearchDTO();
		productSearchDTO.setSearchType(map.get("searchType"));
		productSearchDTO.setKeyWord(map.get("keyWord"));
		productSearchDTO.setCategoryId(map.get("categoryId"));
		productSearchDTO.setRegStart(map.get("regStart"));
		productSearchDTO.setRegEnd(map.get("regEnd"));
		productSearchDTO.setStatus(map.get("status"));
		
		int pageNo = Integer.parseInt(map.get("pageNo"));
		
		
		String searchType = productSearchDTO.getSearchType();
		String keyWord = productSearchDTO.getKeyWord();
		String categoryId = productSearchDTO.getCategoryId();
		String regStart =  productSearchDTO.getRegStart();
		String regEnd = productSearchDTO.getRegEnd();
		String status = productSearchDTO.getStatus();
		
		if(keyWord.equals("")) {
			log.info("null");
		}
		
		if(categoryId.equals("None")) {
			productSearchDTO.setCategoryId("");
		}
		
		int totalRows = productService.getTotalProductNum(productSearchDTO);
		PagerDTO pagerDTO = new PagerDTO(10, 5, totalRows, pageNo);
		
		productSearchDTO.setStartRow(pagerDTO.getStartRowNo());
		productSearchDTO.setEndRow(pagerDTO.getEndRowNo());
		
		
		model.addAttribute("pager", pagerDTO);
		
		log.info("총 RO개수: "+totalRows);
		
		log.info(searchType);
		log.info(keyWord);
		log.info(categoryId);
		log.info("regStart: "+regStart);
		log.info("regEnd: "+regEnd);
		log.info(status);
		
		List<ProductDTO> productList = productService.getProductList(productSearchDTO);
		log.info(productList.toString());
		model.addAttribute("productList", productList);

		return "/product/productListFragment";
	}
}
