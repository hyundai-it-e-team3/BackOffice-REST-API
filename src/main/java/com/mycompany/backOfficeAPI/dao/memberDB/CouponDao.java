package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Coupon;
import com.mycompany.backOfficeAPI.dto.member.SearchTypeCoupon;

@Mapper
public  interface CouponDao {
	public void insertCoupon(Coupon coupon);
	public int getTotalCouponNum(SearchTypeCoupon searchTypeCoupon);
	public List<Coupon> getAllCouponByPage(@Param(value="pager") Pager pager, @Param(value="searchTypeCoupon") SearchTypeCoupon searchTypeCoupon);
	public Coupon getCoupon(String couponId);
	public void updateCoupon(Coupon coupon);
	public void deleteCoupon(List<String> couponId);
}
