package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.member.Wishlist;

@Mapper
public interface WishlistDao {
	public List<Wishlist> getWishlist(String memberId);
	public void insertProduct(Wishlist wishlist);
	public void deleteProduct(Wishlist wishlist);
}
