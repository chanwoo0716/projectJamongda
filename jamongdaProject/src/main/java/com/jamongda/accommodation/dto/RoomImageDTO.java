package com.jamongda.accommodation.dto;

import org.springframework.stereotype.Component;

@Component("roomImageDTO")
public class RoomImageDTO {
	
	private int ro_image_id;	//객실 이미지 고유 ID
	private String ro_image;	//객실 이미지 파일 이름
	private int ro_id;			//객실 고유 ID
	
	public int getRo_image_id() {
		return ro_image_id;
	}
	public void setRo_image_id(int ro_image_id) {
		this.ro_image_id = ro_image_id;
	}
	public String getRo_image() {
		return ro_image;
	}
	public void setRo_image(String ro_image) {
		this.ro_image = ro_image;
	}
	public int getRo_id() {
		return ro_id;
	}
	public void setRo_id(int ro_id) {
		this.ro_id = ro_id;
	}
}