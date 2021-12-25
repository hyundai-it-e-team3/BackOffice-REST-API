package com.mycompany.backOfficeAPI.dao.memberDB;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.member.Member;

@Mapper
public interface AddressDao {
	public void updateAddress(Member member);
}
