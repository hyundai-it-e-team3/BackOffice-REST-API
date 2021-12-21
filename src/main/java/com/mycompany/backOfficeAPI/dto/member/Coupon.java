package com.mycompany.backOfficeAPI.dto.member;

import lombok.Data;

@Data
public class Coupon {
	private String couponId;
	private String name;
	private char type;	//1: 할인 금액,2: 할인 %
	private int amount; // 할인 금액 혹은 할인 %
	private String content;
	private int issuance; // 쿠폰 발행량
	private int stock; // 쿠폰 재고
	private char expDateType; //1: 정해진 날짜, 2: 발행일로부터 +일
	private String expDate;
	private char status; //0: 비활성화, 1:활성화
	private int eventSeq;
}
