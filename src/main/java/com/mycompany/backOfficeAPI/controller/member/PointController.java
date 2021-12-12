package com.mycompany.backOfficeAPI.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public List<Point> getPointList(@PathVariable String memberId){
		log.info("포인트내역 조회 실행");
		List<Point> pointList = pointService.getPointList(memberId);
		return pointList;
	}
	
}
