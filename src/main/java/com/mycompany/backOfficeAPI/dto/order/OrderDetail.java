package com.mycompany.backOfficeAPI.dto.order;

import lombok.Data;

@Data
public class OrderDetail {
	String productDetailId;
	String psize;
	String orderId;
	int amount;
	int price;
	String state;
	String deliveryNo;
	
	String brand;
	String name;
	String color;
}
