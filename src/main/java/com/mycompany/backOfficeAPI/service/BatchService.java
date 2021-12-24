package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.DetailPointDao;
import com.mycompany.backOfficeAPI.dao.memberDB.MemberDao;
import com.mycompany.backOfficeAPI.dao.memberDB.PointDao;
import com.mycompany.backOfficeAPI.dao.orderDB.OrderDao;
import com.mycompany.backOfficeAPI.dto.batch.OrderTotalPriceForMonthOfMember;
import com.mycompany.backOfficeAPI.dto.member.DetailPoint;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BatchService {
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private MemberDao memberDao;
	
	@Resource
	private DetailPointDao detailPointDao;
	
	@Resource
	private PointDao pointDao;
	
	//멈버십 업데이트
	public List<OrderTotalPriceForMonthOfMember> getOrderTotalPriceForMonthOfMember() {
		log.info("월별 회원의 주문 금액 가져오기");
		return orderDao.getOrderTotalPriceForMonthOfMember();
	}
	
	public void updateMemberLevel(String level, List<String> memberIdList) {
		log.info("회원 레벨 업데이터");
		memberDao.updateMemberLevel(level, memberIdList);
	}
	
	//만료 포인트 소멸
	public List<DetailPoint> getExpiryDetailPoint() {
		log.info("기간 만료된 포인트 내역 조회 실행");
		return detailPointDao.getExpiryDetailPoint();
	}
	
	public void updateExpiryDetailPoint(DetailPoint detailPoint) {
		log.info("기간 만료된 상세 포인트 내역 소멸 실행");
		detailPointDao.updateExpiryDetailPoint(detailPoint);
	}
	
	public void insertExpiryPoint(DetailPoint detailPoint) {
		log.info("소멸 포인트 내역 추가");
		pointDao.insertExpiryPoint(detailPoint);
	}
}
