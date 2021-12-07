package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.member.Point;

@Mapper
public interface PointDao {
	public List<Point> getPointList(String memberId);
	public void insertSavePoint(Point savePoint);
	public void insertUsePoint(Point usePoint);
}
