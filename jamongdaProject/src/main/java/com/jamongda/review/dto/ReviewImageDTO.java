package com.jamongda.review.dto;

import org.springframework.stereotype.Component;

@Component("reviewImgDTO")
public class ReviewImageDTO {
	private int rev_image_id;
	private String rev_image;
	private int rev_id;
	
	public int getRev_image_id() {
		return rev_image_id;
	}
	public void setRev_image_id(int rev_image_id) {
		this.rev_image_id = rev_image_id;
	}
	public String getRev_image() {
		return rev_image;
	}
	public void setRev_image(String rev_image) {
		this.rev_image = rev_image;
	}
	public int getRev_id() {
		return rev_id;
	}
	public void setRev_id(int rev_id) {
		this.rev_id = rev_id;
	}
}
