package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.CouponDao;
import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Coupon;
import com.mycompany.backOfficeAPI.dto.member.SearchTypeCoupon;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CouponService {
	
	@Resource
	private CouponDao couponDao;
	
	public String insertCoupon(Coupon coupon) {
		try {
			couponDao.insertCoupon(coupon);
			return "success";
		} catch (DuplicateKeyException e) {
			return "fail";
		}
	}
	
	public int getTotalCouponNum(SearchTypeCoupon searchTypeCoupon) {
		return couponDao.getTotalCouponNum(searchTypeCoupon);
	}
	
	public List<Coupon> getAllCouponByPage(Pager pager, SearchTypeCoupon searchTypeCoupon) {
		return couponDao.getAllCouponByPage(pager, searchTypeCoupon);
	}
	
	public Coupon getCoupon(String couponId) {
		return couponDao.getCoupon(couponId);
	}
	
	public String updateCoupon(Coupon coupon) {
		try {
			couponDao.updateCoupon(coupon);
			return "success";
		} catch (Exception e) {
			log.info(e.toString());
			return "fail";
		}
	}
	
	public String deleteCoupon(List<String> couponId) {
		try {
			couponDao.deleteCoupon(couponId);
			return "success";
		} catch (Exception e) {
			log.info(e.toString());
			return "fail";
		}
	}
}
