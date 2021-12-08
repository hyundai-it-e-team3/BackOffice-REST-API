package com.mycompany.backOfficeAPI.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.orderDB.OTimelineDao;
import com.mycompany.backOfficeAPI.dao.orderDB.OdTimelineDao;
import com.mycompany.backOfficeAPI.dao.orderDB.OrderDao;
import com.mycompany.backOfficeAPI.dao.orderDB.OrderDetailDao;
import com.mycompany.backOfficeAPI.dao.orderDB.PTimelineDao;
import com.mycompany.backOfficeAPI.dao.orderDB.PaymentDao;
import com.mycompany.backOfficeAPI.dto.order.Order;
import com.mycompany.backOfficeAPI.dto.order.OrderDetail;
import com.mycompany.backOfficeAPI.dto.order.OrderInfo;
import com.mycompany.backOfficeAPI.dto.order.Payment;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {
	public enum OrderResult {
		SUCCESS,
		FAIL
	}
	
	@Resource
	PointService pointService;
	@Resource
	StockService stockService;
	
	@Resource
	private OrderDao orderDao;
	@Resource
	private OrderDetailDao orderDetailDao;
	@Resource
	private PaymentDao paymentDao;
	@Resource 
	private OdTimelineDao odTimelineDao;
	@Resource
	private OTimelineDao oTimelineDao;
	@Resource
	private PTimelineDao pTimelineDao;
	
	public Map<String, Object> getOrderInfo(
			String orderId) {
		
		Map<String, Object> map = new HashMap<>();
		log.info(orderId);
		Order order = orderDao.selectByOid(orderId);
		
		if(order == null) {
			map.put("result", "fail");
		} else {
			map.put("result", "success");
			map.put("order", order);
			
			List<OrderDetail> odList = orderDetailDao.selectByOid(orderId);
			List<Payment> payment = paymentDao.selectByOid(orderId);
			
			map.put("orderDetails", odList);
			map.put("payments", payment);
		}
		return map;
	}
	
	
	public List<Order> getOrders(String memberId) {
		log.info("실행");
		return orderDao.selectByMid(memberId);
	}
	
	
	public void updateState(Order order) {
		log.info("실행");
		orderDao.updateState(order);
		oTimelineDao.insert(order);
	}
	
	
	public List<OrderDetail> getOrderDetail(String orderId) {
		log.info("실행");
		return orderDetailDao.selectByOid(orderId);
	}
	
	public void updateState(OrderDetail orderDetail) {
		log.info("실행");
		orderDetailDao.updateState(orderDetail);
		odTimelineDao.insert(orderDetail);
	}
	
	public List<Payment> getPayments(String orderId) {
		log.info("실행");
		return paymentDao.selectByOid(orderId);
	}
	
	public void updateState(Payment payment) {
		log.info("실행");
		paymentDao.updateState(payment);
		pTimelineDao.insert(payment);
	}
	
	public List<OrderDetail> getOrderProducts(String orderId) {
		log.info("실행");
		return orderDetailDao.selectByOid(orderId);
	}
	
	public List<OrderInfo> getOrderInfoList() {
		return orderDao.selectOrderList();
	}

}
