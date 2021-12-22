package com.mycompany.backOfficeAPI.run;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.mycompany.backOfficeAPI.dto.batch.OrderTotalPriceForMonthOfMember;
import com.mycompany.backOfficeAPI.service.BatchService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemberLevelUpdateJob extends QuartzJobBean {
	
	@Resource
	private BatchService batchService;
	
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("매월 1일 자정마다 멤버십 레벨 업데이트 실행");
        
        
        // 1. 이전 달 회원별 주문 총액 조회(memberId, totalPrice)
        List<OrderTotalPriceForMonthOfMember> opMemberList = batchService.getOrderTotalPriceForMonthOfMember();
        
        // 2. 금액별 레벨 매칭
        List<String> level1 = new ArrayList<String>();
        List<String> level2 = new ArrayList<String>();
        List<String> level3 = new ArrayList<String>();
        List<String> level4 = new ArrayList<String>();
        List<String> level5 = new ArrayList<String>();
        List<String> level6 = new ArrayList<String>();
        
        for(OrderTotalPriceForMonthOfMember opMember : opMemberList) {
        	if(opMember.getTotalPrice() < 1000000) {
        		level1.add(opMember.getMemberId());
        	} else if(opMember.getTotalPrice() >= 1000000 || opMember.getTotalPrice() < 2000000) {
        		level2.add(opMember.getMemberId());
        	} else if(opMember.getTotalPrice() >= 2000000 || opMember.getTotalPrice() < 3000000) {
        		level3.add(opMember.getMemberId());
        	} else if(opMember.getTotalPrice() >= 3000000 || opMember.getTotalPrice() < 4000000) {
        		level4.add(opMember.getMemberId());
        	} else if(opMember.getTotalPrice() >= 4000000 || opMember.getTotalPrice() < 5000000) {
        		level5.add(opMember.getMemberId());
        	} else if(opMember.getTotalPrice() >= 5000000) {
        		level6.add(opMember.getMemberId());
        	} 
        }
        
        // 2. (멤버DB) 레벨 별로 멤버 업데이터
        batchService.updateMemberLevel("1", level1);
        batchService.updateMemberLevel("2", level2);
        batchService.updateMemberLevel("3", level3);
        batchService.updateMemberLevel("4", level4);
        batchService.updateMemberLevel("5", level5);
        batchService.updateMemberLevel("6", level6);
    }
    
}
