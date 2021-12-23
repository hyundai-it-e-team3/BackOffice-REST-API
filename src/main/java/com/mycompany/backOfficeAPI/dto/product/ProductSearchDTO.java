package com.mycompany.backOfficeAPI.dto.product;

import lombok.Data;

@Data
public class ProductSearchDTO {
	private String searchType;
	private String keyWord;
	private String categoryId;
	private String regStart;
	private String regEnd;
	private String status;
	private String sortId;
	private String pageNo;
	private String brandName;
	private int mdStatus;
	private int startRow;
	private int endRow;
}
