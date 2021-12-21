package com.mycompany.backOfficeAPI.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.ManagerDao;
import com.mycompany.backOfficeAPI.dto.member.Manager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ManagerService {
	
	@Resource
	private ManagerDao managerDao;
	
	public Manager getManager(String id) {
		log.info("로그인 처리 과정 실행");
		
		try {
			Manager manager = managerDao.getManager(id);
			return manager;
		} catch (Exception e) {
			log.info(e.toString());
			return null;
		}
	}
}
