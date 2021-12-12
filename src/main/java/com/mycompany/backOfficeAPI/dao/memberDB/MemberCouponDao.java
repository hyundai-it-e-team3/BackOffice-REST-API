package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.MemberCoupon;

@Mapper
public interface MemberCouponDao {
	public List<MemberCoupon> getMemberCouponByPage(@Param(value="memberId") String memberId, @Param(value="pager") Pager pager);
	public int getTotalMemberCouponNum(String memberId);
	public void insertMemberCoupon(MemberCoupon memberCoupon);
	public void updateMemberCoupon(MemberCoupon memberCoupon);
	public MemberCoupon getDuplicateCoupon(MemberCoupon memberCoupon);
}
