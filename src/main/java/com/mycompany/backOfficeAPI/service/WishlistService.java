package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.WishlistDao;
import com.mycompany.backOfficeAPI.dto.member.Wishlist;

@Service
public class WishlistService {
	@Resource
	private WishlistDao wishlistDao;
	
	public List<Wishlist> getWishlist(String memberId) {
		return wishlistDao.getWishlist(memberId);
	}
	
	public void insertProduct(Wishlist wishlist) {
		wishlistDao.insertProduct(wishlist);
	}
	
	public void deleteProduct(Wishlist wishlist) {
		wishlistDao.deleteProduct(wishlist);
	}
}
