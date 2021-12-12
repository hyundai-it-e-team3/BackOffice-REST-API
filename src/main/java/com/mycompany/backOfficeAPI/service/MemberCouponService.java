package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.MemberCouponDao;
import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.MemberCoupon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberCouponService {
	
	public enum InsertMemberCouponResult {
		SUCCESS,
		DUPLICATE
	}
	
	@Resource
	private MemberCouponDao memberCouponDao;
	
	public List<MemberCoupon> getMemberCouponByPage(String memberId, Pager pager) {
		return memberCouponDao.getMemberCouponByPage(memberId, pager);
	}
	
	public int getTotalMemberCouponNum(String memberId) {
		return memberCouponDao.getTotalMemberCouponNum(memberId);
	}
	
	public InsertMemberCouponResult insertMemberCoupon(MemberCoupon memberCoupon) {
		//중복 발급 방지 로직 처리
		InsertMemberCouponResult insertMemberCouponResult;
		MemberCoupon dbMemberCoupon = memberCouponDao.getDuplicateCoupon(memberCoupon);
		
		if(dbMemberCoupon != null) {
			log.info("중복 쿠폰 존재");
			insertMemberCouponResult = InsertMemberCouponResult.DUPLICATE;
		} else {
			memberCouponDao.insertMemberCoupon(memberCoupon);
			insertMemberCouponResult = InsertMemberCouponResult.SUCCESS;
			log.info("쿠폰 발급");
		}
		
		return insertMemberCouponResult;
	}
	
	public void updateMemberCoupon(MemberCoupon memberCoupon) {
		memberCouponDao.updateMemberCoupon(memberCoupon);
	}
	
}
