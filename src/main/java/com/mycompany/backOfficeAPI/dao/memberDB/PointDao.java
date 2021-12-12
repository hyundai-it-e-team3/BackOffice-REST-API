package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Point;

@Mapper
public interface PointDao {
	public int getTotalPointNum(String memberid);
	public List<Point> getPointListByPage(@Param(value="memberId") String memberId, @Param(value="pager") Pager pager);
}
