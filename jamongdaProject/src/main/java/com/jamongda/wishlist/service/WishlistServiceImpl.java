package com.jamongda.wishlist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jamongda.wishlist.dao.WishlistDAO;

@Service("wishlistService")
public class WishlistServiceImpl implements WishlistService{
	
	@Autowired
	private WishlistDAO wishlistDAO;

	@Override
	public boolean toggleWish(String email, int acc_id) {
	    try {
	        int count = wishlistDAO.checkWishlist(email, acc_id);
	        if (count > 0) {
	            wishlistDAO.deleteWishlist(email, acc_id);
	            return false;
	        } else {
	            wishlistDAO.addToWishlist(email, acc_id);
	            return true;
	        }
	    } catch (DataAccessException e) {
	        // 로그에 예외를 기록
	        e.printStackTrace();
	        throw new RuntimeException("데이터베이스 오류 발생", e);
	    }
	}

}