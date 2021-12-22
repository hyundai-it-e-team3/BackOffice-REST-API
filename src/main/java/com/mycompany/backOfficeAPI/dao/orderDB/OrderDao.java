package com.mycompany.backOfficeAPI.dao.orderDB;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.backOfficeAPI.dto.Pager;
import com.mycompany.backOfficeAPI.dto.batch.OrderTotalPriceForMonthOfMember;
import com.mycompany.backOfficeAPI.dto.order.Order;
import com.mycompany.backOfficeAPI.dto.order.OrderInfo;
import com.mycompany.backOfficeAPI.dto.order.OrderSearch;

@Mapper
public interface OrderDao {
	public List<Order> selectByMid(String memberId);
	public void updateState(Order order);
	public Order selectByOid(String orderId);
	public Order selectByOidMid(String orderId, String memberId);
	public List<OrderInfo> selectOrderList();

	//Pagination
	public List<OrderInfo> selectByPage(Pager pager);
	public int count();
	
	//Search
	public List<OrderInfo> selectBySearch(OrderSearch orderSearch);
	public int countSearch(OrderSearch orderSearch);
	public List<OrderInfo> selectBySearchMap(Map<String,Object> map);
	public int countSearchMap(Map<String,Object> map);

	public List<OrderInfo> getMemberOrderByPager(@Param(value="memberId") String memberId, @Param(value="pager") Pager pager);
	public int getTotalMemberOrderNum(String memberId);
	
	//멤버십 레벨 업데이트
	public List<OrderTotalPriceForMonthOfMember> getOrderTotalPriceForMonthOfMember();	
}
