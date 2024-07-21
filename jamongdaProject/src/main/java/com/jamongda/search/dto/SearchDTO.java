package com.jamongda.search.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("searchDTO")
public class SearchDTO {
	private String acc_area;
	private String acc_name;
	private Date acc_checkIn;
	private Date acc_checkOut;
	private int acc_person;
	private int acc_id;
	
	public SearchDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public SearchDTO(String acc_area,String acc_name, Date acc_checkIn,Date acc_checkOut,int acc_person,int acc_id) {
		this.acc_area=acc_area;
		this.acc_name=acc_name;
		this.acc_checkIn=acc_checkIn;
		this.acc_checkOut=acc_checkOut;
		this.acc_person=acc_person;
		this.acc_id=acc_id;
	}
	
	public String getAcc_area() {
		return acc_area;
	}
	public void setAcc_area(String acc_area) {
		this.acc_area = acc_area;
	}
	public String getAcc_name() {
		return acc_name;
	}
	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}
	public Date getAcc_checkIn() {
		return acc_checkIn;
	}
	public void setAcc_checkIn(Date acc_checkIn) {
		this.acc_checkIn = acc_checkIn;
	}
	public Date getAcc_checkOut() {
		return acc_checkOut;
	}
	public void setAcc_checkOut(Date acc_checkOut) {
		this.acc_checkOut = acc_checkOut;
	}
	public int getAcc_person() {
		return acc_person;
	}
	public void setAcc_person(int acc_person) {
		this.acc_person = acc_person;
	}
	public int getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(int acc_id) {
		this.acc_id = acc_id;
	}
	
	
}
