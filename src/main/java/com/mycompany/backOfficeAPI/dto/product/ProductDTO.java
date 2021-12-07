package com.mycompany.backOfficeAPI.dto.product;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ProductDTO {
	private String productId;
	private String name;
	private int price;
	private String content;
	private int hitCount;
	private int status;
	private Date regDate;
	private String brandName;
	private String thumbnail;
	private List<ProductDetailDTO> productDetailList;
	private ProductDetailDTO productDetail;
}
