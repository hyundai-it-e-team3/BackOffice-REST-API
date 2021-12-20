package com.mycompany.backOfficeAPI.dto.order;

import lombok.Data;

@Data
public class Payment{
	String type;
	String typeCode;
	int price;
	String accountNo;
	String bank;
	int installment;
	String orderId;
	String state;
	String stateCode;
	
}
