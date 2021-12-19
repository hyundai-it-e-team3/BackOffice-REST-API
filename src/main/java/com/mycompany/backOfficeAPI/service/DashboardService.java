package com.mycompany.backOfficeAPI.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public Map<String, Object> getQuaterSales() {
		List<SaleDashboard> QuaterSales = dashboardDao.selectQuaterSales();
		
		List<String> labels = new ArrayList<String>();
		List<Integer> data = new ArrayList<Integer>();
		
		for(SaleDashboard sale : QuaterSales) {
			labels.add(sale.getSquater());
			data.add(sale.getSale());
		}
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("labels", labels);
		result.put("data", data);
		
		int lastYear = ( data.get(7) - data.get(3) ) * 100 / data.get(3) ;
		int lastQuater = ( data.get(7) - data.get(6) ) * 100 / data.get(6) ;
		
		result.put("lastYear", lastYear);
		result.put("lastQuater", lastQuater);
		
		return result;
	}
	
	public Map<String,Object> getDailySales() {
		List<SaleDashboard> DailySales = dashboardDao.selectDailySales();
		
		List<String> labels = new ArrayList<String>();
		List<Integer> data = new ArrayList<Integer>();
		
		for(SaleDashboard sale : DailySales) {
			labels.add(sale.getSdate().getMonth() + "/" + sale.getSdate().getDay());
			data.add(sale.getSale());
		}
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("labels", labels);
		result.put("data", data);
		
		return result;
	}
	
	public Map<String, Object> getBrandSales() {
		List<SaleDashboard> BrandSales = dashboardDao.selectBrandSales();
		
		List<String> labels = new ArrayList<String>();
		List<Integer> data = new ArrayList<Integer>();
		
		for(SaleDashboard sale : BrandSales) {
			labels.add(sale.getBrand());
			data.add(sale.getSale());
		}
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("labels", labels);
		result.put("data", data);
		
		return result;
	}
	
	public Map<String,Object> getMemberInfo() {
		List<MemberDashboard> memberInfos =  mdashboardDao.selectMemberStat();
		
		int[] menList = new int[7];
		int[] womenList = new int[7];
		int[] totalList = new int[7];
		
		
		for(MemberDashboard member : memberInfos) {
			if(member.getAge() > 6) {
				totalList[6] += member.getValue();
			} else {
				totalList[member.getAge()] = member.getValue();
			}

			if(member.getGender().equals("M")) {
				if(member.getAge() > 6) {
					menList[6] += member.getValue();
				} else {
					menList[member.getAge()] = member.getValue();
				}
				
			} else if(member.getGender().equals("W")) {
				if(member.getAge() > 6) {
					womenList[6] += member.getValue();
				} else {
					womenList[member.getAge()] = member.getValue();
				}
			}
		}
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("menList", menList);
		result.put("womenList", womenList);
		result.put("totalList", totalList);
		
		return result;
	
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
