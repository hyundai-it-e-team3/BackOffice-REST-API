package com.mycompany.backOfficeAPI.dao.orderDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.order.PTimeline;
import com.mycompany.backOfficeAPI.dto.order.Payment;

@Mapper
public interface PTimelineDao {
	void insert(Payment payment);
	List<PTimeline> select(Payment payment);
}
