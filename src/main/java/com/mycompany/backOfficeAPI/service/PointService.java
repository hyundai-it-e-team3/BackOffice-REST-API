package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.PointDao;
import com.mycompany.backOfficeAPI.dto.member.Point;

@Service
public class PointService {
	
	@Resource
	private PointDao pointDao;
	
	public List<Point> getPointList(String memberId){
		return pointDao.getPointList(memberId);
	}

}
