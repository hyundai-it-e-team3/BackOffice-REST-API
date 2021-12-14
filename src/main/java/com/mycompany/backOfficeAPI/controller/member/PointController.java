package com.mycompany.backOfficeAPI.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.PagerAndPoint;
import com.mycompany.backOfficeAPI.dto.member.Point;
import com.mycompany.backOfficeAPI.service.PointService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/point")
public class PointController {
	
	@Resource
	private PointService pointService;
	
	//포인트내역 조회
	@GetMapping("/list/{memberId}")
	public PagerAndPoint getPointList(@RequestParam(defaultValue="1") int pageNo, @PathVariable String memberId){
		log.info("포인트내역 조회 실행");
		log.info(" " + pageNo);
		
		int totalRows = pointService.getTotalPointNum(memberId);
		Pager pager = new Pager(5, 5, totalRows, pageNo);
		
		log.info(pager.getStartRowNo() + " ");
		log.info(pager.getEndRowNo() + " ");
		
		List<Point> pointList = pointService.getPointListByPage(memberId, pager);
		
		PagerAndPoint data = new PagerAndPoint();
		data.setPager(pager);
		data.setPoint(pointList);
		log.info(pointList.toString());
		
		return data;
	}
	
}
