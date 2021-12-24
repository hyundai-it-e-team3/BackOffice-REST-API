package com.mycompany.backOfficeAPI.run;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.mycompany.backOfficeAPI.dto.member.DetailPoint;
import com.mycompany.backOfficeAPI.service.BatchService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DeleteExpiryPointJob extends QuartzJobBean {

	@Resource
	private BatchService batchService;
	
	@Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("매일 0시1분마다 기간 만료(30일) 포인트 소멸 실행");
        
        // 1. 현재 날짜와 만료 일자가 같은 포인트 내역 조회
        List<DetailPoint> expiryDetailPointList = batchService.getExpiryDetailPoint();
        
        // 2. 기간 만료된 상세 포인트 내역들 소멸 실행
        for(DetailPoint expiryDetailPoint : expiryDetailPointList) {
        	batchService.updateExpiryDetailPoint(expiryDetailPoint);
        }
        
        // 3. 포인트 내역에 소멸 내역 추가
        for(DetailPoint expiryDetailPoint : expiryDetailPointList) {
        	batchService.insertExpiryPoint(expiryDetailPoint);
        }
    }
}
