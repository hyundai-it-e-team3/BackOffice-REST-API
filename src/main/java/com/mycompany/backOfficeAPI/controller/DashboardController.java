package com.mycompany.backOfficeAPI.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public Map<String, Object> getQurterSales() {
		return dashboardService.getQuaterSales();
	}
	
	@GetMapping("/daily")
	public Map<String, Object> getDailySales(){
		return dashboardService.getDailySales();
	}
	
	@GetMapping("/brand")
	public Map<String, Object> getBrandSales() {
		log.info(dashboardService.getBrandSales().toString());
		return dashboardService.getBrandSales();
	}
	
	@GetMapping("/member")
	public Map<String, Object> getMemberInfo() {
		return dashboardService.getMemberInfo();
	}
	
	@PostMapping("/quater")
	public void setQurterSales() {
		dashboardService.setQuaterSales();
	}
	
	@PostMapping("/daily")
	public void setDailySales(){
		dashboardService.setDailySales();
	}
	
	@PostMapping("/brand")
	public void setBrandSales() {
		dashboardService.setBrandSales();
	}
	
	@PostMapping("/member")
	public void setMemberInfo() {
		dashboardService.setMemberInfo();
	}
	
}
