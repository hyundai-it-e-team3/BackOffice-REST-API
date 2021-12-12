package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.MemberDao;
import com.mycompany.backOfficeAPI.dto.member.Member;
import com.mycompany.backOfficeAPI.dto.member.MemberForOrder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	
	@Resource
	private MemberDao memberDao;
	
	//로그인 시 마지막 로그인 날짜 업데이트
	public void updateLastLoginDate(String memberId) {
		log.info("실행");
		memberDao.updateLastLoginDate(memberId);
	}
	
	//회원정보 조회
	public List<Member> getAllMember() {
		return memberDao.getAllMember();
	}
	
	//전체 회원정보 조회
	public Member getMember(String memberId) {
		return memberDao.getMember(memberId);
	}
	
	//주문을 위한 회원정보 조회
	public MemberForOrder getMemberForOrder(String memberId) {
		return memberDao.getMemberForOrder(memberId);
	}
	
}
