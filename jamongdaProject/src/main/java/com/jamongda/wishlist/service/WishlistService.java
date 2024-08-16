package com.jamongda.wishlist.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface WishlistService {

	// 찜목록에 추가
	public boolean toggleWish(String email, int acc_id) throws DataAccessException;
	
	public List<Map<String, Object>> getWishlistByUser(String email, int pageSize, int offset) throws DataAccessException;
}
