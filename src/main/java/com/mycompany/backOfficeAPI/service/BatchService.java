package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.MemberDao;
import com.mycompany.backOfficeAPI.dao.orderDB.OrderDao;
import com.mycompany.backOfficeAPI.dto.batch.OrderTotalPriceForMonthOfMember;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BatchService {
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private MemberDao memberDao;
	
	public List<OrderTotalPriceForMonthOfMember> getOrderTotalPriceForMonthOfMember() {
		log.info("월별 회원의 주문 금액 가져오기");
		List<OrderTotalPriceForMonthOfMember> opm = orderDao.getOrderTotalPriceForMonthOfMember();
		return opm;
	}
	
	public void updateMemberLevel(String level, List<String> memberIdList) {
		log.info("회원 레벨 업데이터");
		memberDao.updateMemberLevel(level, memberIdList);
	}
}
