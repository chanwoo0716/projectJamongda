package com.jamongda.accommodation.dto;

import org.springframework.stereotype.Component;

@Component("accommodationImageDTO")
public class AccommodationImageDTO {

	private int acc_image_id;	//숙소 이미지 고유ID(AI처리)
	private String acc_image;	//숙소 이미지 파일이름
	private int acc_id;			//숙소 고유 ID(accommodation 테이블과 FK)
	
	public int getAcc_image_id() {
		return acc_image_id;
	}
	public void setAcc_image_id(int acc_image_id) {
		this.acc_image_id = acc_image_id;
	}
	public String getAcc_image() {
		return acc_image;
	}
	public void setAcc_image(String acc_image) {
		this.acc_image = acc_image;
	}
	public int getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(int acc_id) {
		this.acc_id = acc_id;
	}
}