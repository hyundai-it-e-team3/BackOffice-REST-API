package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.member.Event;
import com.mycompany.backOfficeAPI.dto.member.SearchTypeEvent;

@Mapper
public interface EventDao {
	public int getEventNum(SearchTypeEvent searchTypeEvent);
	public List<Event> getEventByPage(@Param(value="pager") Pager pager, @Param(value="searchTypeEvent") SearchTypeEvent searchTypeEvent);
	public Event getEvent(int eventSeq);
	public void insertEvent(Event event);
	public void updateEvent(Event event);
	public void deleteEvent(List<String> eventSeq);
}
