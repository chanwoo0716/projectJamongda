package com.jamongda.accommodation.dto;

import org.springframework.stereotype.Component;

@Component("roomDTO")
public class RoomDTO {
	
	private int ro_id;				//객실 고유 ID
	private String ro_name;			//객실 이름
	private int ro_min;				//기준 수용 인원
	private int ro_max;				//최대 수용 인원
	private String ro_info;			//객실 정보
	private String ro_amenities;	//객실 편의시설
	private int ro_price;			//객실 가격
	private String ro_checkIn;		//입실 시간
	private String ro_checkOut;		//퇴실 시간
	private int acc_id;				//숙소 고유 id
	
	public int getRo_id() {
		return ro_id;
	}
	public void setRo_id(int ro_id) {
		this.ro_id = ro_id;
	}
	public String getRo_name() {
		return ro_name;
	}
	public void setRo_name(String ro_name) {
		this.ro_name = ro_name;
	}
	public int getRo_min() {
		return ro_min;
	}
	public void setRo_min(int ro_min) {
		this.ro_min = ro_min;
	}
	public int getRo_max() {
		return ro_max;
	}
	public void setRo_max(int ro_max) {
		this.ro_max = ro_max;
	}
	public String getRo_info() {
		return ro_info;
	}
	public void setRo_info(String ro_info) {
		this.ro_info = ro_info;
	}
	public String getRo_amenities() {
		return ro_amenities;
	}
	public void setRo_amenities(String ro_amenities) {
		this.ro_amenities = ro_amenities;
	}
	public int getRo_price() {
		return ro_price;
	}
	public void setRo_price(int ro_price) {
		this.ro_price = ro_price;
	}
	public String getRo_checkIn() {
		return ro_checkIn;
	}
	public void setRo_checkIn(String ro_checkIn) {
		this.ro_checkIn = ro_checkIn;
	}
	public String getRo_checkOut() {
		return ro_checkOut;
	}
	public void setRo_checkOut(String ro_checkOut) {
		this.ro_checkOut = ro_checkOut;
	}
	public int getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(int acc_id) {
		this.acc_id = acc_id;
	}

}