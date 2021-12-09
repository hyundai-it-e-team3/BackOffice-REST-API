package com.mycompany.backOfficeAPI.dto.product;

import lombok.Data;

@Data
public class StockDetailDTO {
	private String brandName;
	private String productId;
	private String name;
	private String productDetailId;
	private String psize;
	private int amount;
}
