package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Member;
import com.mycompany.backOfficeAPI.dto.member.SearchTypeMember;

@Mapper
public interface MemberDao {
	public void updatePointBalance(Member member);
	public int getMemberPoint(String memberId);
	public Member getMember(String memberId);
	public void updateLastLoginDate(String memberId);
	public int getTotalMemberNum(SearchTypeMember searchTypeMember);
	public List<Member> getAllMemberByPage(@Param(value="pager") Pager pager, @Param(value="searchTypeMember") SearchTypeMember searchTypeMember);
	
	//멤버십 레벨 업데이트
	public void updateMemberLevel(@Param(value="level") String level, @Param(value="memberIdList") List<String> memberIdList);
}
