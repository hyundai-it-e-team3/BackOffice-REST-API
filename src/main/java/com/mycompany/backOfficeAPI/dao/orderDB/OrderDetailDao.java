package com.mycompany.backOfficeAPI.dao.orderDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.order.OrderDetail;

@Mapper
public interface OrderDetailDao {
	public List<OrderDetail> selectByOid(String orderId);
	public void updateState(OrderDetail orderDetail);
	public void updateStateByOid(OrderDetail orderDetail);
	public void insert(OrderDetail orderDetail);
}
