package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.MDashboardDao;
import com.mycompany.backOfficeAPI.dao.orderDB.DashboardDao;
import com.mycompany.backOfficeAPI.dto.dashboard.MemberDashboard;
import com.mycompany.backOfficeAPI.dto.dashboard.SaleDashboard;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DashboardService {
	@Resource
	DashboardDao dashboardDao;
	
	@Resource
	MDashboardDao mdashboardDao;
	
	public List<SaleDashboard> getQuaterSales() {
		return dashboardDao.selectQuaterSales();
	}
	
	public List<SaleDashboard> getDailySales() {
		return dashboardDao.selectDailySales();
	}
	
	public List<SaleDashboard> getBrandSales() {
		return dashboardDao.selectBrandSales();
	}
	
	public List<MemberDashboard> getMemberInfo() {
		return mdashboardDao.selectMemberStat();
	}
	
	public void setQuaterSales() {
		log.info("실행");
		dashboardDao.insertQuaterSales();
	}
	
	public void setDailySales() {
		log.info("실행");
		dashboardDao.insertDailySales();
	}
	
	public void setBrandSales() {
		log.info("실행");
		dashboardDao.insertBrandSales();
	}
	
	public void setMemberInfo() {
		log.info("실행");
		mdashboardDao.insertMemberStat();
	}

}
