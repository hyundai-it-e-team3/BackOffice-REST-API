package com.mycompany.backOfficeAPI.dto.order;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Order {
	String orderId;
	String memberId;
	Date orderDate;
	String state;
	String stateCode;
	String request;
	int zipCode;
	String address1;
	String address2;
	String tel;
	String name;
	String couponId;
	List<OrderDetail> orderDetailList;
	List<Payment> paymentList;
	int totalPrice;
	String payType;
}
