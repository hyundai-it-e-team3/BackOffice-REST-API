package com.mycompany.backOfficeAPI.dao.orderDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.dashboard.MemberDashboard;
import com.mycompany.backOfficeAPI.dto.dashboard.SaleDashboard;

@Mapper
public interface DashboardDao {
	List<SaleDashboard> selectQuaterSales();
	List<SaleDashboard> selectDailySales();
	List<SaleDashboard> selectBrandSales();
	void insertQuaterSales();
	void insertDailySales();
	void insertBrandSales();
}
