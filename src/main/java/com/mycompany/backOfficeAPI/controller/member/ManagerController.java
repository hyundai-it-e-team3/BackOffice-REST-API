package com.mycompany.backOfficeAPI.controller.member;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.member.Manager;
import com.mycompany.backOfficeAPI.service.ManagerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/manager")
public class ManagerController {
	
	@Resource
	private ManagerService managerService;
	
	@GetMapping("/{id}")
	public Manager getMember(@PathVariable String id) {
		log.info("로그인을 위한 관리자 정보 조회 실행");
		Manager manager = managerService.getManager(id);
		return manager;
	}
	
}
