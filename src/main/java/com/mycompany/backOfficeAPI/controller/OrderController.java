package com.mycompany.backOfficeAPI.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.order.Order;
import com.mycompany.backOfficeAPI.dto.order.OrderDetail;
import com.mycompany.backOfficeAPI.dto.order.OrderInfo;
import com.mycompany.backOfficeAPI.dto.order.PagerAndOrderInfo;
import com.mycompany.backOfficeAPI.dto.order.Payment;
import com.mycompany.backOfficeAPI.service.OrderService;
import com.mycompany.backOfficeAPI.service.PointService;
import com.mycompany.backOfficeAPI.service.StockService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController{
	@Resource
	private OrderService orderService;
	
	@Resource
	private PointService pointService;
	
	@Resource
	private StockService stockService;
	
	
	@GetMapping("/list")
	public List<Order> list(@RequestParam String memberId) {
		log.info("실행");
		log.info("member : " + memberId);
		
		List<Order> list = orderService.getOrders(memberId); 
		return list;
	}
	
	
	@GetMapping("/{orderId}") 
	public Map<String, Object> getOrderInfo(
			@PathVariable String orderId) {
		log.info("실행");
		log.info("orderId : " + orderId);

		return orderService.getOrderInfo(orderId);
	}
	
	@GetMapping("/{orderId}/list") 
	public List<OrderDetail> getOrderProductList(
			@PathVariable String orderId) {
		log.info("실행");
		log.info("orderId : " + orderId);

		return orderService.getOrderDetail(orderId);
	}
	
	
	@PatchMapping("/state")
	public void updateState(@RequestBody Order order) {
		log.info("실행");
		log.info("order : " + order);
		
		orderService.updateState(order);
	}
	
	@GetMapping("/detail")
	public List<OrderDetail> detail(@RequestBody String orderId) { 
		log.info("실행");
		log.info("orderDetail : " + orderId);
		
		return orderService.getOrderDetail(orderId);
	}
	
	@PostMapping("/detail")
	public void updateState(OrderDetail orderDetail) {
		log.info("실행");
		log.info("orderDetail : " + orderDetail);
		
		orderService.updateState(orderDetail);
	}
	
	@GetMapping("/payment")
	public List<Payment> payment(@RequestBody String orderId) { 
		log.info("실행");
		log.info("orderId : " + orderId);
		
		return orderService.getPayments(orderId);
	}
	
	@PatchMapping("/payment")
	public void updateState(@RequestBody Payment payment) {
		log.info("실행");
		log.info("payment : " + payment);
		
		orderService.updateState(payment);
	}
	
	/*
	 * @GetMapping("/infolist") public List<OrderInfo> list() { log.info("실행");
	 * 
	 * List<OrderInfo> list = orderService.getOrderInfoList(); return list; }
	 */
	
	@GetMapping("/infolist")
	public PagerAndOrderInfo list(@RequestParam(defaultValue="1") int pageNo) {
		log.info("실행");
		
		int totalRows = orderService.getTotalOrderNum();
		Pager pager = new Pager(5,5, totalRows, pageNo);
		List<OrderInfo> list = orderService.getOrderInfoList(pager);
		
		PagerAndOrderInfo data = new PagerAndOrderInfo();
		data.setOrderInfos(list);
		data.setPager(pager);
		
		return data;
	}
	
	@GetMapping("/infolist/{memberId}")
	public PagerAndOrderInfo selectMemberOrderList(@RequestParam(defaultValue="1") int pageNo, @PathVariable String memberId) {
		log.info("회원별 주문내역 조회 실행");
		
		int totalRows = orderService.getTotalOrderNum();
		Pager pager = new Pager(5, 5, totalRows, pageNo);
		List<OrderInfo> list = orderService.getMemberOrderListByPager(memberId, pager);
		
		PagerAndOrderInfo data = new PagerAndOrderInfo();
		data.setOrderInfos(list);
		data.setPager(pager);
		
		return data;
	}
}
