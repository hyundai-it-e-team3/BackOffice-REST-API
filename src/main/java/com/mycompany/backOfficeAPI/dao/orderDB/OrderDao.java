package com.mycompany.backOfficeAPI.dao.orderDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.order.Order;

@Mapper
public interface OrderDao {
	public List<Order> selectByMid(String memberId);
	public void updateState(Order order);
	public Order selectByOid(String orderId);
	public Order selectByOidMid(String orderId, String memberId);
}
