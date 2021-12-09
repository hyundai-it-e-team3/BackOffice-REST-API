package com.mycompany.backOfficeAPI.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.product.PagerDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductDetailDTO;
import com.mycompany.backOfficeAPI.dto.product.StockDTO;
import com.mycompany.backOfficeAPI.service.ProductDetailService;
import com.mycompany.backOfficeAPI.service.StockService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/stock")
public class StockController {
	
	@Resource
	private StockService stockService;
	
	//GET: stock/CM2B0WSC561W_TN/64
	@RequestMapping("/{productDetailId}/{psize}")
	public StockDTO getStock(@PathVariable("productDetailId") String productDetailId, @PathVariable("psize") String psize) {
		StockDTO stockDTO = new StockDTO();
		stockDTO.setProductDetailId(productDetailId);
		stockDTO.setPsize(psize);

		return stockService.getStock(stockDTO);
	}
	
	//POST: stock/plus
	//requestBody => StockDTO
	@PostMapping("/{type}")
	public Map<String,String> updateStock(@PathVariable String type,StockDTO stockDTO) {

		String result = stockService.updateStock(stockDTO,type);
		Map<String, String> mp = new HashMap<>();
		
		mp.put("result",result);
		
		return mp;
	}
	
}
