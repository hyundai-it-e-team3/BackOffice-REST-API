package com.mycompany.backOfficeAPI.dto.batch;

import lombok.Data;

@Data
public class OrderTotalPriceForMonthOfMember {
	private String memberId;
	private int totalPrice;
}
