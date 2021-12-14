package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.PointDao;
import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Point;

@Service
public class PointService {
	
	@Resource
	private PointDao pointDao;
	
	public int getTotalPointNum(String memberId) {
		return pointDao.getTotalPointNum(memberId);
	}
	
	public List<Point> getPointListByPage(String memberId, Pager pager){
		return pointDao.getPointListByPage(memberId, pager);
	}

}
