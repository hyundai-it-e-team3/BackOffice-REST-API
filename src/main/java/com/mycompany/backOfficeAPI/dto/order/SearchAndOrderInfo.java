package com.mycompany.backOfficeAPI.dto.order;

import java.util.List;

import com.mycompany.backOfficeAPI.dto.Pager;

import lombok.Data;

@Data
public class SearchAndOrderInfo {
	private OrderSearch orderSearch;
	private List<OrderInfo> orderInfos;
	private Pager pager;
}
