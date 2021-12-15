package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.orderDB.DashboardDao;
import com.mycompany.backOfficeAPI.dto.dashboard.MemberDashboard;
import com.mycompany.backOfficeAPI.dto.dashboard.SaleDashboard;

@Service
public class DashboardService {
	@Resource
	DashboardDao dashboardDao;
	
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
		return dashboardDao.selectMemberInfo();
	}

}
