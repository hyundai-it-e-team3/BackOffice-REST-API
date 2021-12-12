package com.mycompany.backOfficeAPI.controller.member;

import java.util.List;

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
import com.mycompany.backOfficeAPI.dto.member.MemberCoupon;
import com.mycompany.backOfficeAPI.dto.member.PagerAndMemberCoupon;
import com.mycompany.backOfficeAPI.service.MemberCouponService;
import com.mycompany.backOfficeAPI.service.MemberCouponService.InsertMemberCouponResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member/coupon")
public class MemberCouponContoller {
	
	@Resource
	private MemberCouponService memberCouponService;
	
	@GetMapping("/list/{memberId}")
	public PagerAndMemberCoupon getMemberCoupon(@RequestParam(defaultValue="1") int pageNo, @PathVariable String memberId) {
		log.info("쿠폰 목록 조회 실행");
		
		int totalRows = memberCouponService.getTotalMemberCouponNum(memberId);
		Pager pager = new Pager(5, 5, totalRows, pageNo);
		
		List<MemberCoupon> couponList = memberCouponService.getMemberCouponByPage(memberId, pager);
		
		PagerAndMemberCoupon data = new PagerAndMemberCoupon();
		data.setPager(pager);
		data.setMemberCoupon(couponList);
		
		return data;
	}
	
	@PostMapping
	public InsertMemberCouponResult insertMemberCoupon(@RequestBody MemberCoupon memberCoupon) {
		log.info("쿠폰 발급 실행");
		InsertMemberCouponResult insertMemberCouponResult = memberCouponService.insertMemberCoupon(memberCoupon);
		return insertMemberCouponResult;
	}
	
	@PatchMapping
	public void updateMemberCoupon(@RequestBody MemberCoupon memberCoupon) {
		log.info("쿠폰 사용 실행");
		memberCouponService.updateMemberCoupon(memberCoupon);
	}
	
}
