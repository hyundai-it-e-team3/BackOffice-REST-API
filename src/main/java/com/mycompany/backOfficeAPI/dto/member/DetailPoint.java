package com.mycompany.backOfficeAPI.dto.member;

import java.util.Date;

import lombok.Data;

@Data
public class DetailPoint {
	private int detailPointSeq;
	private String memberId;
	private String type;
	private int pointSeq;
	private char status;
	private Date regDate;
	private int point;
	private int balance;
	private int refDetailPointSeq;
}
