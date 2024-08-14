package com.jamongda.wishlist.service;

import org.springframework.dao.DataAccessException;

public interface WishlistService {

	// 찜목록에 추가
	public boolean toggleWish(String email, int acc_id) throws DataAccessException;
}
