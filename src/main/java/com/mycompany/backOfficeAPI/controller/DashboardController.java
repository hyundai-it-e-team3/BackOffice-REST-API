package com.mycompany.backOfficeAPI.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.dashboard.MemberDashboard;
import com.mycompany.backOfficeAPI.dto.dashboard.SaleDashboard;
import com.mycompany.backOfficeAPI.service.DashboardService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dashboard")
@Slf4j
public class DashboardController {
	@Resource
	DashboardService dashboardService;
	
	@GetMapping("/quater")
	public List<SaleDashboard> getQurterSales() {
		return dashboardService.getQuaterSales();
	}
	
	@GetMapping("/daily")
	public List<SaleDashboard> getDailySales(){
		return dashboardService.getDailySales();
	}
	
	@GetMapping("/brand")
	public List<SaleDashboard> getBrandSales() {
		return dashboardService.getBrandSales();
	}
	
	@GetMapping("/member")
	public List<MemberDashboard> getMemberInfo() {
		return dashboardService.getMemberInfo();
	}
	
}
