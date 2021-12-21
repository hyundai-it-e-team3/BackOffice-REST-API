package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.MemberDao;
import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Member;
import com.mycompany.backOfficeAPI.dto.member.SearchTypeMember;

@Service
public class MemberService {
	
	@Resource
	private MemberDao memberDao;
	
	//회원정보 조회
	public List<Member> getAllMemberByPage(Pager pager, SearchTypeMember searchTypeMember) {
		return memberDao.getAllMemberByPage(pager, searchTypeMember);
	}
	
	//회원정보 전체 행 수 조회
	public int getTotalMemberNum(SearchTypeMember searchTypeMember) {
		return memberDao.getTotalMemberNum(searchTypeMember);
	}
	
}
