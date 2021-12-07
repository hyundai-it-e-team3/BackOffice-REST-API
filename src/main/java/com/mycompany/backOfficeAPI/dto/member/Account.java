package com.mycompany.backOfficeAPI.dto.member;

import lombok.Data;

@Data
public class Account {
	private String accountNo;
	private String bank;
	private char payType;
	private String memberId;
}
