package com.jamongda.booking.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component("bookingDTO")
public class BookingDTO {
	private Long bo_number;
	private String bo_name;
	private String bo_tel;
	private String bo_checkIn;
	private String bo_checkOut;
	private Date payDate;
	private String bo_payment;
	private int bo_price;
	private String imp_uid;
	private int ro_id;
	private String email;
	
	public Long getBo_number() {
		return bo_number;
	}
	public void setBo_number(Long bo_number) {
		this.bo_number = bo_number;
	}
	public String getBo_name() {
		return bo_name;
	}
	public void setBo_name(String bo_name) {
		this.bo_name = bo_name;
	}
	public String getBo_tel() {
		return bo_tel;
	}
	public void setBo_tel(String bo_tel) {
		this.bo_tel = bo_tel;
	}
	public String getBo_checkIn() {
		return bo_checkIn;
	}
	public void setBo_checkIn(String bo_checkIn) {
		this.bo_checkIn = bo_checkIn;
	}
	public String getBo_checkOut() {
		return bo_checkOut;
	}
	public void setBo_checkOut(String bo_checkOut) {
		this.bo_checkOut = bo_checkOut;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public String getBo_payment() {
		return bo_payment;
	}
	public void setBo_payment(String bo_payment) {
		this.bo_payment = bo_payment;
	}
	public int getBo_price() {
		return bo_price;
	}
	public void setBo_price(int bo_price) {
		this.bo_price = bo_price;
	}
	public int getRo_id() {
		return ro_id;
	}
	public void setRo_id(int ro_id) {
		this.ro_id = ro_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImp_uid() {
		return imp_uid;
	}
	public void setImp_uid(String imp_uid) {
		this.imp_uid = imp_uid;
	}
	
}