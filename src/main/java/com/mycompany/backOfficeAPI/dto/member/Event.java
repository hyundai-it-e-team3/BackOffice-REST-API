package com.mycompany.backOfficeAPI.dto.member;

import java.util.Date;

import lombok.Data;

@Data
public class Event {
	private int eventSeq;
	private String name;
	private char eventType;
	private String content;
	private String image;
	private Date regDate;
	private Date startDate;
	private Date expDate;
	private char status;
	private String couponId;
}
