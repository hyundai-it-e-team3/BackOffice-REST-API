package com.mycompany.backOfficeAPI.dto.order;

import java.util.List;

import com.mycompany.backOfficeAPI.dto.Pager;

import lombok.Data;

@Data
public class PagerAndOrderInfo {
	private Pager pager;
	private List<OrderInfo> orderInfos;
}
