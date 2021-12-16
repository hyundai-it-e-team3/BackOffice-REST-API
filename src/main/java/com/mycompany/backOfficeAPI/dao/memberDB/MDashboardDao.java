package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.dashboard.MemberDashboard;

@Mapper
public interface MDashboardDao {
	List<MemberDashboard> selectMemberStat();
	void insertMemberStat();
}
