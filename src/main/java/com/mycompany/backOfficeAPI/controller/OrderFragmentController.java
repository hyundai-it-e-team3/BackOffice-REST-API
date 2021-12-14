package com.mycompany.backOfficeAPI.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.order.OrderInfo;
import com.mycompany.backOfficeAPI.dto.order.OrderSearch;
import com.mycompany.backOfficeAPI.dto.order.SearchAndOrderInfo;
import com.mycompany.backOfficeAPI.service.OrderService;
import com.mycompany.backOfficeAPI.service.PointService;
import com.mycompany.backOfficeAPI.service.StockService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/orderhtml")
public class OrderFragmentController {
	@Resource
	private OrderService orderService;
	
	@Resource
	private PointService pointService;
	
	@Resource
	private StockService stockService;
	
	
	@PostMapping(value="/searchMap",  produces="application/json; charset=UTF-8")
	public String searchList(@RequestBody Map<String, Object> map,  Model model) {
		log.info(map.toString());
		int totalRows = orderService.getSearchNum(map); 
		Pager pager = new Pager(5,5, totalRows, (int)(map.get("pageNo"))); 
		map.put("endRowNo", pager.getEndRowNo());
		map.put("startRowNo", pager.getStartRowNo());
		
		List<OrderInfo> list = orderService.getOrderInfoList(map);
		log.info(list.toString());
		
		SearchAndOrderInfo data = new SearchAndOrderInfo();
		data.setOrderInfos(list);
		data.setPager(pager);
		
		model.addAttribute("orderList", list);
		model.addAttribute("pager", pager);
		
		return "/order/orderListFragment";
	}
	
	@PostMapping(value="/search",  produces="application/json; charset=UTF-8")
	public SearchAndOrderInfo searchList(@RequestBody OrderSearch orderSearch) {
		
		 log.info("실행"); log.info(orderSearch.toString());
		 log.info(orderSearch.getOStateList().toString());
		 
		 int totalRows = orderService.getSearchNum(orderSearch); Pager pager = new
		 Pager(5,5, totalRows, orderSearch.getPageNo()); orderSearch.setPager(pager);
		 List<OrderInfo> list = orderService.getOrderInfoList(orderSearch);
		 log.info(list.toString());
		
		 SearchAndOrderInfo data = new SearchAndOrderInfo();
		
		 return data;
	}

}
