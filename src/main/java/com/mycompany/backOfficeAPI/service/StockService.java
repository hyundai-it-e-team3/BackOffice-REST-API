package com.mycompany.backOfficeAPI.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.productDB.StockDAO;
import com.mycompany.backOfficeAPI.dto.product.StockDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockService {
	
	@Resource
	private StockDAO stockDAO;
	
	public StockDTO getStock(StockDTO stockDTO) {
		log.info("실행");
		log.info(stockDTO.toString());
		return stockDAO.selectByStockDTO(stockDTO);
	}

	public String updateStock(StockDTO stockDTO,String type) {
		log.info("실행");
		log.info(stockDTO.toString());
		
		if(type.equals("plus")) {
			int result = stockDAO.updatePlusByStockDTO(stockDTO);
			return "success";
		}else {
			StockDTO stk = stockDAO.selectByStockDTO(stockDTO);
			
			if(stk ==null) {
				return "error";
			}else {
				int result = stockDAO.updateMinusByStockDTO(stockDTO);
				if(result==0) {
					return "not enought stock";
				}else {
					return "success";
				}
			}			
		}
	}
}
