package com.mycompany.backOfficeAPI.run;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.mycompany.backOfficeAPI.service.DashboardService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UpdateDashboardJob extends QuartzJobBean {
	@Resource
	DashboardService dashboardService;
	
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		log.info("dashBorad 정보 Update 실행");
    	dashboardService.setDailySales();
		dashboardService.setBrandSales();
		dashboardService.setMemberInfo();
    }
}
