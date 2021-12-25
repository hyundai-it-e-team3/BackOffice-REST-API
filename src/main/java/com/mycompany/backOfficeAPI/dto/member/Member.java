package com.mycompany.backOfficeAPI.dto.member;

import java.util.Date;

import lombok.Data;

@Data
public class Member {
	private String memberId;
	private String password;
	private String name;
	private String nickname;
	private String email;
	private String tel;
	private Date birthday;
	private char memberLevel;
	private String oneclickpayPassword;
	private String kakaoNo;
	private String memberRole;
	private char status;
	private Date lastLoginDate;
	private int point;
	private Date regDate;
	private String gender;
	
	private String address1;
	private String address2;
}
