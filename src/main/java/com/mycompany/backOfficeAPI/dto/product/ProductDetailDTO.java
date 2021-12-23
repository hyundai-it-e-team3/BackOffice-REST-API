package com.mycompany.backOfficeAPI.dto.product;

import java.util.List;


import lombok.Data;

@Data
public class ProductDetailDTO {
	private String productDetailId;
	private String withProduct;
	private String colorCode;
	private String colorChip;
	private String productId;
	private String name;
	private String brandName;
	private List<ProductImgDTO> imgList;
	private List<ProductImgDTO> withImgList;
	private List<StockDTO> stockList;
}
