package com.jamongda.wishlist.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("wishlistDAO")
public interface WishlistDAO {
    // 찜목록에서 조회
    int checkWishlist(@Param("email") String email, @Param("acc_id") int acc_id) throws DataAccessException;

    // 찜목록에 추가
    void addToWishlist(@Param("email") String email, @Param("acc_id") int acc_id) throws DataAccessException;

    // 찜목록에 제거
    void deleteWishlist(@Param("email") String email, @Param("acc_id") int acc_id) throws DataAccessException;
}
