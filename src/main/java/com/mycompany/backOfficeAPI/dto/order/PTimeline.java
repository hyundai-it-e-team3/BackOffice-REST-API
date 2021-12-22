package com.mycompany.backOfficeAPI.dto.order;

import java.util.Date;

import lombok.Data;

@Data
public class PTimeline {
	String orderId;
	String type;
	int typeCode;
	Date issueDate;
	String state;
	int stateCode;
}
