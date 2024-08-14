package com.jamongda.wishlist.dto;

import org.springframework.stereotype.Component;

@Component("wishlistDTO")
public class WishlistDTO {
	private int wish_id;
	private String email;
	private int acc_id;
	
	public int getWish_id() {
		return wish_id;
	}
	public void setWish_id(int wish_id) {
		this.wish_id = wish_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(int acc_id) {
		this.acc_id = acc_id;
	}	
}