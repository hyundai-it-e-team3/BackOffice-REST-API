package com.mycompany.backOfficeAPI.dao.memberDB;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.member.Member;
import com.mycompany.backOfficeAPI.dto.member.MemberForOrder;

@Mapper
public interface MemberDao {
	public Member getMember(String memberId);
	public void insertMember(Member member);
	public void deleteMember(String memberId);
	public void updateLastLoginDate(String memberId);
	public void updateMember(Member member);
	public MemberForOrder getMemberForOrder(String memberId);
	public void updatePointBalance(Member member);
	public int getMemberPoint(String memberId);
	public void updateAccountPassword(Member member);
}
