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

	public List<OrderInfo> getOrderInfoList(Map<String, Object> map) {
		log.info("실행");
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
		log.info("상품 환불 실행");
		Order order = orderDao.selectByOid(orderId);

		order.setStateCode(6);
		orderDao.updateState(order);

		// 결제 환불 처리
		log.info("결제 환불 처리");
		List<Payment> paymentList = paymentDao.selectByOid(orderId);
		for (Payment payment : paymentList) {
			// Member DB 환불 처리
			if (payment.getType().equals("쿠폰")) {
				//쿠폰 환불 로직
				MemberCoupon coupon = new MemberCoupon();
				coupon.setMemberId(order.getMemberId());
				coupon.setCouponId(order.getCouponId());

				memberCouponService.refundMemberCoupon(coupon);
			} else if (payment.getType().equals("포인트")) {
				//포인트 환불 로직
				Point point = new Point();
				point.setOrderId(order.getOrderId());
				point.setMemberId(order.getMemberId());
				pointService.insertRefundPoint(point);
			}

			// Order DB 환불 처리
			payment.setStateCode(0);
			paymentDao.updateStateByOid(payment);
		}

		// 상품 환불 처리
		log.info("상품 환불 처리");
		List<OrderDetail> orderDetailList = orderDetailDao.selectByOid(orderId);
		for (OrderDetail orderDetail : orderDetailList) {
			// ProductDB 환불처리
			StockDTO stock = new StockDTO();
			stock.setPsize(orderDetail.getPsize());
			stock.setProductDetailId(orderDetail.getProductDetailId());
			stock.setAmount(orderDetail.getAmount());
			StockResult sr = stockService.addStock(stock);
			if (sr != StockResult.SUCCESS)
				return OrderResult.FAIL;

			// OrderDB 환불처리
			
			log.info("주문 환불 처리");
			orderDetail.setStateCode(0);
			orderDetailDao.updateStateByOid(orderDetail);
		}

		return OrderResult.SUCCESS;
	}

	@Transactional
	public OrderResult reorderWithReturn(String orderId) {
		//주문 정보 조회
		log.info("주문 정보 조회");
		Order orderInfo = orderDao.selectByOid(orderId);
		orderInfo.setOrderDetailList(getOrderDetail(orderId));
		orderInfo.setPaymentList(getPayments(orderId));

		//주문 취소
		log.info("주문 정보 취소");
		cancelOrder(orderId);

		//재주문 정보 생성
		//반품 상품 제거
		log.info("재주문 정보 생성");
		List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
		List<OrderDetail> newOrderDetailList = new ArrayList<OrderDetail>();
		int totalPrice = 0;
		for(OrderDetail orderDetail : orderDetailList) {
			//반품 상품은 빼고 결제
			if(orderDetail.getState().equals("반품"))
				continue;
			totalPrice += orderDetail.getPrice();
			newOrderDetailList.add(orderDetail);
		}
		if(newOrderDetailList.size() > 0) {
			orderInfo.setOrderDetailList(newOrderDetailList);
			orderInfo.setTotalPrice(totalPrice);
			
			
			log.info("결제 정보 생성");
			List<Payment> paymentList = orderInfo.getPaymentList();
			List<Payment> newPaymentList = new ArrayList<Payment>();
			
			//쿠폰 사용했었으면 쿠폰 할인률은 다시 계산
			log.info("쿠폰 할인률 다시 계산");
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
			
			//포인트 사용 금액과 결제 금액을 계산
			log.info("포인트 사용 금액 및 재결제 금액 계산");
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
			
			// 재주문 실행
			log.info("재주문 실행");
			log.info(orderInfo+"");
			insertOrder(orderInfo);
		}
		return OrderResult.SUCCESS;
	}
	
	@Transactional
	public OrderResult insertOrder(Order order) {
		log.info("주문 입력 실행");
		//Order 주문 처리
		orderDao.insert(order);
		oTimelineDao.insert(order);

		//Order 결제 처리
		log.info("주문 결제 처리");
		List<Payment> pList = order.getPaymentList();
		for (Payment payment : pList) {
			//OrderDB 주문 처리
			if (payment.getTypeCode() != 0) {
				payment.setOrderId(order.getOrderId());
				paymentDao.insert(payment);
			}

			//Member DB 주문 처리
			if (payment.getTypeCode() == 1) {
				//쿠폰 사용 처리
				MemberCoupon coupon = new MemberCoupon();
				coupon.setMemberId(order.getMemberId());
				coupon.setCouponId(order.getCouponId());

				memberCouponService.useMemberCoupon(coupon);
			} else if (payment.getTypeCode() == 2) {
				//포인트 사용 처리
				Point usePoint = new Point();
				usePoint.setMemberId(order.getMemberId());
				usePoint.setOrderId(order.getOrderId());
				usePoint.setPoint(payment.getPrice());
				usePoint.setType("사용");

				PointResult pr = pointService.insertUsePoint(usePoint);
				if (pr != PointResult.SUCCESS)
					return OrderResult.FAIL;
			}
		}

		//Order 상품 처리
		log.info("주문 상품 처리");
		List<OrderDetail> odList = order.getOrderDetailList();
		for (OrderDetail orderDetail : odList) {
			//Order DB 주문 처리
			orderDetail.setOrderId(order.getOrderId());
			orderDetail.setStateCode(1);
			orderDetailDao.insert(orderDetail);
			odTimelineDao.insert(orderDetail);

			//Product DB 주문 처리
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
