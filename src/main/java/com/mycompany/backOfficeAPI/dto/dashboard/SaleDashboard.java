package com.mycompany.backOfficeAPI.dto.dashboard;

import java.util.Date;

import lombok.Data;

@Data
public class SaleDashboard {
	Date date;
	String brand;
	int sale;
}
