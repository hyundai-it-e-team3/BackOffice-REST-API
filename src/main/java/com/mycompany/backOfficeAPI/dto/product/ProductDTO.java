package com.mycompany.backOfficeAPI.dto.product;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ProductDTO {
	private String productId;
	private String name;
	private int price;
	private int purchaseAmount;
	private String content;
	private int hitCount;
	private int status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date regDate;
	private String brandName;
	private String thumbnail;
	private String categoryId;
	private String parentCategoryId;
	private int clevel;
	private String categoryName;
	private int mdStatus;
	private List<ProductDetailDTO> productDetailList;
	private List<String> categoryList;
}
