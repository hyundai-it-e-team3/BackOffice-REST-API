package com.mycompany.backOfficeAPI.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Member;
import com.mycompany.backOfficeAPI.dto.member.PagerAndMember;
import com.mycompany.backOfficeAPI.dto.member.SearchTypeMember;
import com.mycompany.backOfficeAPI.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Resource
	private MemberService memberService;

	@PostMapping
	public PagerAndMember getAllMemberByPage(@RequestParam(defaultValue="1") int pageNo, 
											 @RequestBody SearchTypeMember searchTypeMember) {
		log.info("회원 목록 조회 실행");
		
		int totalRows = memberService.getTotalMemberNum(searchTypeMember);
		Pager pager = new Pager(5, 5, totalRows, pageNo);
		
		List<Member> memberList = memberService.getAllMemberByPage(pager, searchTypeMember);

		PagerAndMember data = new PagerAndMember();
		data.setPager(pager);
		data.setMember(memberList);
		
		return data;
	}
	
}
