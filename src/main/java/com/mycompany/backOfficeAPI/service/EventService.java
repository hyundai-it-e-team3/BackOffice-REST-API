package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.EventDao;
import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Event;
import com.mycompany.backOfficeAPI.dto.member.SearchTypeEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventService {
	
	@Resource
	private EventDao eventDao;
	
	public int getEventNum(SearchTypeEvent searchTypeEvent) {
		return eventDao.getEventNum(searchTypeEvent);
	}
	
	public List<Event> getEventByPage(Pager pager, SearchTypeEvent searchTypeEvent) {
		return eventDao.getEventByPage(pager, searchTypeEvent);
	}
	
	public Event getEvent(int eventSeq) {
		return eventDao.getEvent(eventSeq);
	}
	
	public String insertEvent(Event event) {
		try {
			eventDao.insertEvent(event);
			return "success";
		} catch (Exception e) {
			return "fail";
		}
	}
	
	public String updateEvent(Event event) {
		try {
			eventDao.updateEvent(event);
			return "success";
		} catch (Exception e) {
			log.info(e.toString());
			return "fail";
		}
	}
	
	public String deleteEvent(List<String> eventSeq) {
		try {
			eventDao.deleteEvent(eventSeq);
			return "success";
		} catch (Exception e) {
			log.info(e.toString());
			return "fail";
		}
	}
}
