package com.mycompany.backOfficeAPI.dao.orderDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.order.Cart;

@Mapper
public interface CartDao {
	public List<Cart> selectByMid(String memberId);
	public void update(Cart cart);
	public void delete(String cartId);
	public Cart selectCart(Cart cart);
	public Cart selectByCid(String cartId);
	public void updateAmount(Cart cart);
}
