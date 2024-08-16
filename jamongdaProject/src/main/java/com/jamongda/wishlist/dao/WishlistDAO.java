package com.jamongda.wishlist.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("wishlistDAO")
public interface WishlistDAO {
	// 찜목록에서 조회
	public int checkWishlist(@Param("email") String email, @Param("acc_id") int acc_id) throws DataAccessException;

	// 찜목록에 추가
	public void addToWishlist(@Param("email") String email, @Param("acc_id") int acc_id) throws DataAccessException;

	// 찜목록에 제거
	public void deleteWishlist(@Param("email") String email, @Param("acc_id") int acc_id) throws DataAccessException;

	// 마이페이지 찜목록 출력
	List<Map<String, Object>> selectWishlistByUser(@Param("email") String email, @Param("pageSize") int pageSize,
			@Param("offset") int offset);
}
