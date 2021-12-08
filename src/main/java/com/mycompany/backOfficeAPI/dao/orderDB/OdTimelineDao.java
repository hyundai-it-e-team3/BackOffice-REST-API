package com.mycompany.backOfficeAPI.dao.orderDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.order.OdTimeline;
import com.mycompany.backOfficeAPI.dto.order.OrderDetail;

@Mapper
public interface OdTimelineDao {
	void insert(OrderDetail orderDetail);
	List<OdTimeline> select(OrderDetail orderDetail);
}
