package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Member;
import com.mycompany.backOfficeAPI.dto.member.MemberForOrder;

@Mapper
public interface MemberDao {
	public void updateLastLoginDate(String memberId);
	public Member getMember(String memberId);
	public int getTotalMemberNum();
	public List<Member> getAllMemberByPage(Pager pager);
	public MemberForOrder getMemberForOrder(String memberId);
}
