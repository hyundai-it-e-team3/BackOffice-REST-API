package com.mycompany.backOfficeAPI.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Event;
import com.mycompany.backOfficeAPI.dto.member.PagerAndEvent;
import com.mycompany.backOfficeAPI.dto.member.SearchTypeEvent;
import com.mycompany.backOfficeAPI.service.EventService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {

	@Resource
	private EventService eventService;
	
	@PostMapping("/list")
	public PagerAndEvent getEventByPage(@RequestParam(defaultValue="1") int pageNo, 
										@RequestBody SearchTypeEvent searchTypeEvent) {
		log.info("이벤트 목록 조회 실행");
		
		int totalRows = eventService.getEventNum(searchTypeEvent);
		Pager pager = new Pager(5, 5, totalRows, pageNo);
		
		List<Event> eventList = eventService.getEventByPage(pager, searchTypeEvent);
		
		PagerAndEvent data = new PagerAndEvent();
		data.setPager(pager);
		data.setEvent(eventList);
		
		return data;
	}
	
	@PostMapping
	public String insertEvent(@RequestBody Event event) {
		log.info("이벤트 등록 실행");
		String result = eventService.insertEvent(event);
		return result;
	}

	@GetMapping("/{eventSeq}")
	public Event getEvent(@PathVariable int eventSeq) {
		log.info("이벤트 조회 실행");
		Event event = eventService.getEvent(eventSeq);
		return event;
	}
	
	@PostMapping("/update")
	public String updateEvent(@RequestBody Event event) {
		log.info("이벤트 수정 실행");
		String result = eventService.updateEvent(event);
		return result;
	}
	
	@PostMapping("/delete")
	public String deleteEvent(@RequestBody List<String> eventSeq) {
		log.info("이벤트 삭제 실행");
		String result = eventService.deleteEvent(eventSeq);
		return result;
	}
}
