package com.mycompany.backOfficeAPI.dto.member;

import java.util.List;

import com.mycompany.backOfficeAPI.dto.Pager;

import lombok.Data;

@Data
public class PagerAndCoupon {
	private Pager pager;
	private List<Coupon> coupon;
}
