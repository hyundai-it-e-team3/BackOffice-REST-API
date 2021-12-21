package com.mycompany.backOfficeAPI.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Coupon;
import com.mycompany.backOfficeAPI.dto.member.PagerAndCoupon;
import com.mycompany.backOfficeAPI.dto.member.SearchTypeCoupon;
import com.mycompany.backOfficeAPI.service.CouponService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/coupon")
public class CouponContoller {
	
	@Resource
	private CouponService couponService;
	
	@PostMapping("/list")
	public PagerAndCoupon getAllCouponByPage(@RequestParam(defaultValue="1") int pageNo, 
											 @RequestBody SearchTypeCoupon searchTypeCoupon) {
		log.info("쿠폰 목록 조회 실행");
		
		int totalRows = couponService.getTotalCouponNum(searchTypeCoupon);
		Pager pager = new Pager(5, 5, totalRows, pageNo);
		
		List<Coupon> couponList = couponService.getAllCouponByPage(pager, searchTypeCoupon);
		
		PagerAndCoupon data = new PagerAndCoupon();
		data.setPager(pager);
		data.setCoupon(couponList);
		
		return data;
	}
	
	@GetMapping("{couponId}")
	public Coupon getCoupon(@PathVariable String couponId) {
		log.info("쿠폰 조회 실행");
		Coupon coupon = couponService.getCoupon(couponId);
		return coupon;
	}
	
	@PostMapping
	public String insertCoupon(@RequestBody Coupon coupon) {
		log.info("쿠폰 등록 실행");
		String result = couponService.insertCoupon(coupon);
		return result;
	}
	
	@PostMapping("/update")
	public String updateCoupon(@RequestBody Coupon coupon) {
		log.info("쿠폰 수정 실행");
		String result = couponService.updateCoupon(coupon);
		return result;
	}
	
	@PostMapping("/delete")
	public String deleteCoupon(@RequestBody List<String> couponId) {
		log.info("쿠폰 삭제 실행");
		String result = couponService.deleteCoupon(couponId);
		return result;
	}
}
