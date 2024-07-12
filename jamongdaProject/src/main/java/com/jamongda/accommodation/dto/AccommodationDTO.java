package com.jamongda.accommodation.dto;

import org.springframework.stereotype.Component;

@Component("accommodationDTO")
public class AccommodationDTO {

	private int acc_id;			//숙소 고유 id(PK), (*articleNo랑 같음)
	private String acc_name;	//숙소 이름
	private String acc_type;	//숙소 유형
	private String acc_area;	//숙소 지역
	private String acc_tel;		//숙소 전화번호
	private String acc_address;	//숙소 주소
	private String acc_info;	//숙소 정보
	private String email;		//회원 이메일(*사업자는 쿼리문에서 roll='사업자' 조건걸어줘야함)
	
	public int getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(int acc_id) {
		this.acc_id = acc_id;
	}
	public String getAcc_name() {
		return acc_name;
	}
	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}
	public String getAcc_type() {
		return acc_type;
	}
	public void setAcc_type(String acc_type) {
		this.acc_type = acc_type;
	}
	public String getAcc_area() {
		return acc_area;
	}
	public void setAcc_area(String acc_area) {
		this.acc_area = acc_area;
	}
	public String getAcc_tel() {
		return acc_tel;
	}
	public void setAcc_tel(String acc_tel) {
		this.acc_tel = acc_tel;
	}
	public String getAcc_address() {
		return acc_address;
	}
	public void setAcc_address(String acc_address) {
		this.acc_address = acc_address;
	}
	public String getAcc_info() {
		return acc_info;
	}
	public void setAcc_info(String acc_info) {
		this.acc_info = acc_info;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
