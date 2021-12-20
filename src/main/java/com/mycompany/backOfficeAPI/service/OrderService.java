package com.mycompany.backOfficeAPI.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.orderDB.OTimelineDao;
import com.mycompany.backOfficeAPI.dao.orderDB.OdTimelineDao;
import com.mycompany.backOfficeAPI.dao.orderDB.OrderDao;
import com.mycompany.backOfficeAPI.dao.orderDB.OrderDetailDao;
import com.mycompany.backOfficeAPI.dao.orderDB.PTimelineDao;
import com.mycompany.backOfficeAPI.dao.orderDB.PaymentDao;
import com.mycompany.backOfficeAPI.dao.productDB.ProductDAO;
import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.order.Order;
import com.mycompany.backOfficeAPI.dto.order.OrderDetail;
import com.mycompany.backOfficeAPI.dto.order.OrderInfo;
import com.mycompany.backOfficeAPI.dto.order.OrderSearch;
import com.mycompany.backOfficeAPI.dto.order.Payment;
import com.mycompany.backOfficeAPI.dto.product.ProductDTO;

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
	@Resource
	private ProductDAO productDao;
	
	public Map<String, Object> getOrderInfo(
			String orderId) {
		
		Map<String, Object> map = new HashMap<>();
		log.info(orderId);
		Order order = orderDao.selectByOid(orderId);
		
		if(order == null) {
			map.put("result", "fail");
		} else {
			map.put("result", "success");
			
			List<OrderDetail> odList = orderDetailDao.selectByOid(orderId);
			List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
			for(OrderDetail orderDetail : odList) {
				String productDetailId = orderDetail.getProductDetailId();
				StringTokenizer st = new StringTokenizer(productDetailId, "_");
				String productId = st.nextToken();
				ProductDTO product = productDao.selectByProductId(productId);
				orderDetail.setBrand(product.getBrandName());
				orderDetail.setColor(st.nextToken());
				orderDetail.setName(product.getName());
				orderDetails.add(orderDetail);
			}
			map.put("orderDetails", orderDetails);
			
			List<Payment> payments = paymentDao.selectByOid(orderId);
			map.put("payments", payments);
			
			String payType = "";
			int totalPrice = 0;
			for(Payment payment : payments) {
				payType += payment.getType() + " ";
				totalPrice += payment.getPrice();
			}
			
			order.setPayType(payType);
			order.setTotalPrice(totalPrice);
			
			map.put("order", order);
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
	
	@Transactional
	public void updateState(OrderDetail orderDetail) {
		log.info("실행");
		log.info(orderDetail.toString());
		orderDetailDao.updateState(orderDetail);
		odTimelineDao.insert(orderDetail);
		String stateCode =  orderDetail.getStateCode();
		Order order = new Order();
		order.setOrderId(orderDetail.getOrderId());
		switch(stateCode) {
			case "6":
				order.setStateCode("0");
				break;
			default :
				order.setStateCode("2");
		}
		orderDao.updateState(order);
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
	
	public List<OrderInfo> getOrderInfoList(Pager pager) {
		return orderDao.selectByPage(pager);
	}
	
	public List<OrderInfo> getOrderInfoList(OrderSearch orderSearch) {
		log.info("실행");
		return orderDao.selectBySearch(orderSearch);
	}
	
	public int getTotalOrderNum() {
		return orderDao.count();
	}
	
	public int getSearchNum(OrderSearch orderSearch) {
		return orderDao.countSearch(orderSearch);
	}
	
	public List<OrderInfo> getOrderInfoList(Map<String,Object> map) {
		log.info("실행");
		return orderDao.selectBySearchMap(map);
	}
	
	public int getSearchNum(Map<String,Object> map) {
		return orderDao.countSearchMap(map);
	}
	
	public List<OrderInfo> getMemberOrderByPager(String memberId, Pager pager) {
		return orderDao.getMemberOrderByPager(memberId, pager);
	}
	
	public int getTotalMemberOrderNum(String memberId) {
		return orderDao.getTotalMemberOrderNum(memberId);
	}

}
