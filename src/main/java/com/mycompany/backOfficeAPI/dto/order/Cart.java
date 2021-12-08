package com.mycompany.backOfficeAPI.dto.order;

import lombok.Data;

@Data
public class Cart {
	String productDetailId;
	String psize;
	int amount;
	String memberId;
	String cartId;
}
