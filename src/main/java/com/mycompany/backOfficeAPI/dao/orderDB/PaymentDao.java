package com.mycompany.backOfficeAPI.dao.orderDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.order.Payment;

@Mapper
public interface PaymentDao {
	public List<Payment> selectByOid(String orderId);
	public void updateState(Payment payment);
	public void updateStateByOid(Payment payment);
	public void insert(Payment payment);
}
