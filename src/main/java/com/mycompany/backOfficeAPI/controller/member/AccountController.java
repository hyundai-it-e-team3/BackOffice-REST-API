package com.mycompany.backOfficeAPI.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.member.Account;
import com.mycompany.backOfficeAPI.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

	@Resource
	private AccountService accountService;
	
	@GetMapping("/{memberId}")
	public List<Account> getAccount(@PathVariable String memberId) {
		log.info("결제수단 조회");
		List<Account> account = accountService.getAccount(memberId);
		return account;
	}
	
	@DeleteMapping
	public void deleteAccount(@RequestParam String accountNo) {
		log.info("결제수단 삭제");
		accountService.deleteAccount(accountNo);
	}
	
}
