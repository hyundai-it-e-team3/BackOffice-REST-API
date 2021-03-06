package com.mycompany.backOfficeAPI.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.MemberCouponDao;
import com.mycompany.backOfficeAPI.dao.orderDB.OTimelineDao;
import com.mycompany.backOfficeAPI.dao.orderDB.OdTimelineDao;
import com.mycompany.backOfficeAPI.dao.orderDB.OrderDao;
import com.mycompany.backOfficeAPI.dao.orderDB.OrderDetailDao;
import com.mycompany.backOfficeAPI.dao.orderDB.PTimelineDao;
import com.mycompany.backOfficeAPI.dao.orderDB.PaymentDao;
import com.mycompany.backOfficeAPI.dao.productDB.ProductDAO;
import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.MemberCoupon;
import com.mycompany.backOfficeAPI.dto.member.Point;
import com.mycompany.backOfficeAPI.dto.order.Order;
import com.mycompany.backOfficeAPI.dto.order.OrderDetail;
import com.mycompany.backOfficeAPI.dto.order.OrderInfo;
import com.mycompany.backOfficeAPI.dto.order.OrderSearch;
import com.mycompany.backOfficeAPI.dto.order.Payment;
import com.mycompany.backOfficeAPI.dto.product.ProductDTO;
import com.mycompany.backOfficeAPI.dto.product.StockDTO;
import com.mycompany.backOfficeAPI.service.PointService.PointResult;
import com.mycompany.backOfficeAPI.service.StockService.StockResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {
	public enum OrderResult {
		SUCCESS, FAIL
	}

	@Resource
	PointService pointService;
	@Resource
	StockService stockService;
	@Resource
	MemberCouponService memberCouponService;

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
	@Resource
	private MemberCouponDao memberCouponDao;

	public Map<String, Object> getOrderInfo(String orderId) {

		Map<String, Object> map = new HashMap<>();
		log.info(orderId);
		Order order = orderDao.selectByOid(orderId);

		if (order == null) {
			map.put("result", "fail");
		} else {
			map.put("result", "success");

			List<OrderDetail> odList = orderDetailDao.selectByOid(orderId);
			List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
			for (OrderDetail orderDetail : odList) {
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
			for (Payment payment : payments) {
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
		log.info("??????");
		return orderDao.selectByMid(memberId);
	}

	public void updateState(Order order) {
		log.info("??????");
		orderDao.updateState(order);
		oTimelineDao.insert(order);
	}

	public List<OrderDetail> getOrderDetail(String orderId) {
		log.info("??????");
		return orderDetailDao.selectByOid(orderId);
	}

	@Transactional
	public void updateState(OrderDetail orderDetail) {
		log.info("??????");
		log.info(orderDetail.toString());
		orderDetailDao.updateState(orderDetail);
		odTimelineDao.insert(orderDetail);
		int stateCode = orderDetail.getStateCode();
		Order order = new Order();
		order.setOrderId(orderDetail.getOrderId());
		switch (stateCode) {
		case 6:
			order.setStateCode(0);
			break;
		default:
			order.setStateCode(2);
		}
		orderDao.updateState(order);
	}

	public List<Payment> getPayments(String orderId) {
		log.info("??????");
		return paymentDao.selectByOid(orderId);
	}

	public void updateState(Payment payment) {
		log.info("??????");
		paymentDao.updateState(payment);
		pTimelineDao.insert(payment);
	}

	public List<OrderDetail> getOrderProducts(String orderId) {
		log.info("??????");
		return orderDetailDao.selectByOid(orderId);
	}

	public List<OrderInfo> getOrderInfoList(Pager pager) {
		return orderDao.selectByPage(pager);
	}

	public List<OrderInfo> getOrderInfoList(OrderSearch orderSearch) {
		log.info("??????");
		return orderDao.selectBySearch(orderSearch);
	}

	public int getTotalOrderNum() {
		return orderDao.count();
	}

	public int getSearchNum(OrderSearch orderSearch) {
		return orderDao.countSearch(orderSearch);
	}

	public List<OrderInfo> getOrderInfoList(Map<String, Object> map) {
		log.info("??????");
		return orderDao.selectBySearchMap(map);
	}

	public int getSearchNum(Map<String, Object> map) {
		return orderDao.countSearchMap(map);
	}

	public List<OrderInfo> getMemberOrderByPager(String memberId, Pager pager) {
		return orderDao.getMemberOrderByPager(memberId, pager);
	}

	public int getTotalMemberOrderNum(String memberId) {
		return orderDao.getTotalMemberOrderNum(memberId);
	}
	
	@Transactional
	public OrderResult cancelOrder(String orderId) {
		log.info("?????? ?????? ??????");
		Order order = orderDao.selectByOid(orderId);

		order.setStateCode(6);
		orderDao.updateState(order);

		// ?????? ?????? ??????
		log.info("?????? ?????? ??????");
		List<Payment> paymentList = paymentDao.selectByOid(orderId);
		for (Payment payment : paymentList) {
			// Member DB ?????? ??????
			if (payment.getType().equals("??????")) {
				//?????? ?????? ??????
				MemberCoupon coupon = new MemberCoupon();
				coupon.setMemberId(order.getMemberId());
				coupon.setCouponId(order.getCouponId());

				memberCouponService.refundMemberCoupon(coupon);
			} else if (payment.getType().equals("?????????")) {
				//????????? ?????? ??????
				Point point = new Point();
				point.setOrderId(order.getOrderId());
				point.setMemberId(order.getMemberId());
				pointService.insertRefundPoint(point);
			}

			// Order DB ?????? ??????
			payment.setStateCode(0);
			paymentDao.updateStateByOid(payment);
		}

		// ?????? ?????? ??????
		log.info("?????? ?????? ??????");
		List<OrderDetail> orderDetailList = orderDetailDao.selectByOid(orderId);
		for (OrderDetail orderDetail : orderDetailList) {
			// ProductDB ????????????
			StockDTO stock = new StockDTO();
			stock.setPsize(orderDetail.getPsize());
			stock.setProductDetailId(orderDetail.getProductDetailId());
			stock.setAmount(orderDetail.getAmount());
			StockResult sr = stockService.addStock(stock);
			if (sr != StockResult.SUCCESS)
				return OrderResult.FAIL;

			// OrderDB ????????????
			
			log.info("?????? ?????? ??????");
			orderDetail.setStateCode(0);
			orderDetailDao.updateStateByOid(orderDetail);
		}

		return OrderResult.SUCCESS;
	}

	@Transactional
	public OrderResult reorderWithReturn(String orderId) {
		//?????? ?????? ??????
		log.info("?????? ?????? ??????");
		Order orderInfo = orderDao.selectByOid(orderId);
		orderInfo.setOrderDetailList(getOrderDetail(orderId));
		orderInfo.setPaymentList(getPayments(orderId));

		//?????? ??????
		log.info("?????? ?????? ??????");
		cancelOrder(orderId);

		//????????? ?????? ??????
		//?????? ?????? ??????
		log.info("????????? ?????? ??????");
		List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
		List<OrderDetail> newOrderDetailList = new ArrayList<OrderDetail>();
		int totalPrice = 0;
		for(OrderDetail orderDetail : orderDetailList) {
			//?????? ????????? ?????? ??????
			if(orderDetail.getState().equals("??????"))
				continue;
			totalPrice += orderDetail.getPrice();
			newOrderDetailList.add(orderDetail);
		}
		if(newOrderDetailList.size() > 0) {
			orderInfo.setOrderDetailList(newOrderDetailList);
			orderInfo.setTotalPrice(totalPrice);
			
			
			log.info("?????? ?????? ??????");
			List<Payment> paymentList = orderInfo.getPaymentList();
			List<Payment> newPaymentList = new ArrayList<Payment>();
			
			//?????? ?????????????????? ?????? ???????????? ?????? ??????
			log.info("?????? ????????? ?????? ??????");
			if(orderInfo.getCouponId() != null && orderInfo.getCouponId() != "") {
				MemberCoupon param = new MemberCoupon();
				param.setCouponId(orderInfo.getCouponId());
				param.setMemberId(orderInfo.getMemberId());
				MemberCoupon memberCoupon = memberCouponDao.getMemberCouponByMemberCoupon(param);
				Payment payment = new Payment();
				payment.setOrderId(orderInfo.getOrderId());
				payment.setStateCode(1);
				payment.setTypeCode(1);
				if(memberCoupon.getType() == '2') {
					int couponPrice = totalPrice * memberCoupon.getAmount() / 100;
					payment.setPrice(couponPrice);
					totalPrice -= couponPrice;
				} else {
					payment.setPrice(memberCoupon.getAmount());
					totalPrice -= memberCoupon.getAmount();
				}
				
				newPaymentList.add(payment);
			}
			
			//????????? ?????? ????????? ?????? ????????? ??????
			log.info("????????? ?????? ?????? ??? ????????? ?????? ??????");
			Payment mainPayment;
			for(Payment payment : paymentList) {
				if(payment.getTypeCode() == 1) {
					continue;
				} else if(payment.getTypeCode() == 2) {
					if(payment.getPrice() > totalPrice) {
						payment.setPrice(totalPrice);
						payment.setStateCode(1);
						newPaymentList.add(payment);
					} else {
						totalPrice -= payment.getPrice();
						payment.setStateCode(1);
						newPaymentList.add(payment);
					}
				} else {
					mainPayment = payment;
					mainPayment.setPrice(totalPrice);
					mainPayment.setStateCode(1);
					mainPayment.setTypeCode(payment.getTypeCode());
					newPaymentList.add(mainPayment);
				}
			}
			orderInfo.setPaymentList(newPaymentList);
			
			// ????????? ??????
			log.info("????????? ??????");
			log.info(orderInfo+"");
			insertOrder(orderInfo);
		}
		return OrderResult.SUCCESS;
	}
	
	@Transactional
	public OrderResult insertOrder(Order order) {
		log.info("?????? ?????? ??????");
		//Order ?????? ??????
		orderDao.insert(order);
		oTimelineDao.insert(order);

		//Order ?????? ??????
		log.info("?????? ?????? ??????");
		List<Payment> pList = order.getPaymentList();
		for (Payment payment : pList) {
			//OrderDB ?????? ??????
			if (payment.getTypeCode() != 0) {
				payment.setOrderId(order.getOrderId());
				paymentDao.insert(payment);
			}

			//Member DB ?????? ??????
			if (payment.getTypeCode() == 1) {
				//?????? ?????? ??????
				MemberCoupon coupon = new MemberCoupon();
				coupon.setMemberId(order.getMemberId());
				coupon.setCouponId(order.getCouponId());

				memberCouponService.useMemberCoupon(coupon);
			} else if (payment.getTypeCode() == 2) {
				//????????? ?????? ??????
				Point usePoint = new Point();
				usePoint.setMemberId(order.getMemberId());
				usePoint.setOrderId(order.getOrderId());
				usePoint.setPoint(payment.getPrice());
				usePoint.setType("??????");

				PointResult pr = pointService.insertUsePoint(usePoint);
				if (pr != PointResult.SUCCESS)
					return OrderResult.FAIL;
			}
		}

		//Order ?????? ??????
		log.info("?????? ?????? ??????");
		List<OrderDetail> odList = order.getOrderDetailList();
		for (OrderDetail orderDetail : odList) {
			//Order DB ?????? ??????
			orderDetail.setOrderId(order.getOrderId());
			orderDetail.setStateCode(1);
			orderDetailDao.insert(orderDetail);
			odTimelineDao.insert(orderDetail);

			//Product DB ?????? ??????
			StockDTO stock = new StockDTO();
			stock.setPsize(orderDetail.getPsize());
			stock.setProductDetailId(orderDetail.getProductDetailId());
			stock.setAmount(orderDetail.getAmount());
			StockResult sr = stockService.minusStock(stock);
			if (sr != StockResult.SUCCESS)
				return OrderResult.FAIL;
		}
		return OrderResult.SUCCESS;
	}

}
