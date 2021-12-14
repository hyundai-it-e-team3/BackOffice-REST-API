package com.mycompany.backOfficeAPI.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Member;
import com.mycompany.backOfficeAPI.dto.member.PagerAndMember;
import com.mycompany.backOfficeAPI.dto.member.SearchTypeMember;
import com.mycompany.backOfficeAPI.security.JwtUtil;
import com.mycompany.backOfficeAPI.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Resource
	private PasswordEncoder passwordEncoder;
	
	@Resource
	private MemberService memberService;
	
	@Resource
	private AuthenticationManager authenticationManager;
	
	@PatchMapping("/login")
	public Map<String, String> login(@RequestBody Member member) {
		log.info("로그인 실행");
		
		String memberId = member.getMemberId();
		String password = member.getPassword();
		
		Map<String, String> map = new HashMap<>();
		
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberId, password);
			Authentication authentication = authenticationManager.authenticate(token); //아이디, 비밀번호 인증 여부 확인 후 객체 생성
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
		
			//최근 로그인 날짜 업데이트
			memberService.updateLastLoginDate(memberId);
			
			String authority = authentication.getAuthorities().iterator().next().toString();
			map.put("result", "success");
			map.put("memberId", memberId);
			map.put("jwt", JwtUtil.createToken(memberId, authority));
		} catch (DisabledException de) {
			map.put("result", "disabledMember");
		}
		
		return map;
	}

	@PostMapping
	public PagerAndMember getAllMemberByPage(@RequestParam(defaultValue="1") int pageNo, 
											 @RequestBody SearchTypeMember searchTypeMember) {
		log.info("회원정보 조회");
		
		int totalRows = memberService.getTotalMemberNum();
		Pager pager = new Pager(5, 5, totalRows, pageNo);
		
		List<Member> memberList = memberService.getAllMemberByPage(pager, searchTypeMember);

		PagerAndMember data = new PagerAndMember();
		data.setPager(pager);
		data.setMember(memberList);
		
		return data;
	}
	
}
