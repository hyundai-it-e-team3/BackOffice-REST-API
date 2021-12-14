package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Member;
import com.mycompany.backOfficeAPI.dto.member.SearchTypeMember;

@Mapper
public interface MemberDao {
	public void updateLastLoginDate(String memberId);
	public int getTotalMemberNum();
	public List<Member> getAllMemberByPage(@Param(value="pager") Pager pager, @Param(value="searchTypeMember") SearchTypeMember searchTypeMember);
}
