package com.mycompany.backOfficeAPI.dao.memberDB;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.member.Manager;

@Mapper
public interface ManagerDao {
	public Manager getManager(String id);
}
