package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.backOfficeAPI.dao.memberDB.AddressDao;
import com.mycompany.backOfficeAPI.dao.memberDB.MemberDao;
import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Member;
import com.mycompany.backOfficeAPI.dto.member.SearchTypeMember;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
	
	@Resource
	private MemberDao memberDao;
	
	@Resource
	private AddressDao addressDao;
	
	//회원목록 조회
	public List<Member> getAllMemberByPage(Pager pager, SearchTypeMember searchTypeMember) {
		return memberDao.getAllMemberByPage(pager, searchTypeMember);
	}
	
	//회원정보 전체 행 수 조회
	public int getTotalMemberNum(SearchTypeMember searchTypeMember) {
		return memberDao.getTotalMemberNum(searchTypeMember);
	}
	
	//회원정보 조회
	public Member getMember(String memberId) {
		return memberDao.getMember(memberId);
	}
	
	//회원정보 수정
	@Transactional
	public String updateMember(Member member) {
		try {
			memberDao.updateMember(member);
			addressDao.updateAddress(member);
			return "success";
		} catch (Exception e) {
			log.info(e.toString());
			return "fail";
		}
	}
	
}
