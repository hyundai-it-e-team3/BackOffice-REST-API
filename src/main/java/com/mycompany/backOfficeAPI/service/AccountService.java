package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.AccountDao;
import com.mycompany.backOfficeAPI.dao.memberDB.MemberDao;
import com.mycompany.backOfficeAPI.dto.member.Account;
import com.mycompany.backOfficeAPI.dto.member.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountService {
	
	public enum InsertAccountResult {
		SUCCESS,
		REQUIREDPASSWORD
	}
	
	@Resource
	private AccountDao accountDao;
	
	@Resource
	private MemberDao memberDao;
	
	public List<Account> getAccount(String memberId) {
		return accountDao.getAccount(memberId);
	}
	
	public InsertAccountResult insertAccount(Account account) {
		Member member = memberDao.getMember(account.getMemberId());
		String oneclickpayPassword = member.getOneclickpayPassword();
		
		if(oneclickpayPassword == null) {
			log.info("결제비밀번호 등록 후 계좌를 등록하시오.");
			return InsertAccountResult.REQUIREDPASSWORD;
		} else {
			accountDao.insertAccount(account);
			log.info("결제수단 등록 성공");
			return InsertAccountResult.SUCCESS;
		}
	}
	
	public void deleteAccount(String accountNo) {
		accountDao.deleteAccount(accountNo);
	}
	
}
