package com.mycompany.backOfficeAPI.dto.order;

import java.util.Date;
import java.util.List;

import com.mycompany.backOfficeAPI.dto.Pager;

import lombok.Data;

@Data
public class OrderSearch {
	private List<String> odStateList;
	private List<String> oStateList;
	private String searchType;
	private String searchWord;
	private Date startDate;
	private Date endDate;
	private String sortId;
	private int pageNo;
	
	private Pager pager;
}
