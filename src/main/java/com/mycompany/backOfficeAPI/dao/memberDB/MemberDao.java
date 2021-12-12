package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.member.Member;
import com.mycompany.backOfficeAPI.dto.member.MemberForOrder;

@Mapper
public interface MemberDao {
	public void updateLastLoginDate(String memberId);
	public Member getMember(String memberId);
	public List<Member> getAllMember();
	public MemberForOrder getMemberForOrder(String memberId);
}
