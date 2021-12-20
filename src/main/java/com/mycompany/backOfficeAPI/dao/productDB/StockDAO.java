package com.mycompany.backOfficeAPI.dao.productDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.product.StockDTO;

@Mapper
public interface StockDAO {
	public StockDTO selectByStockDTO(StockDTO stockDTO);
	public List<StockDTO> selectByProductDetailId(String productDetailId);
	public int updatePlusByStockDTO(StockDTO stockDTO);
	public int updateMinusByStockDTO(StockDTO stockDTO);
	public void insertStock(StockDTO stockDTO);
	public List<String> selectAllSize();
}
