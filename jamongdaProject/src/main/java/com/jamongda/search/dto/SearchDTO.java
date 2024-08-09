package com.jamongda.search.dto;

import org.springframework.stereotype.Component;

@Component("searchDTO")
public class SearchDTO {
   private int acc_id;
   private String acc_name;
   private String acc_type;
   private String acc_area;
   private String acc_tel;
   private String acc_address;
   private String acc_info;
   private String email;
   private String regCheck;
   private String rejectReason;   
   private String acc_image;
   private int ro_price;
   private String checkIn;
   private String checkOut;
   
   public SearchDTO() {
      
   }

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

   public String getRegCheck() {
      return regCheck;
   }

   public void setRegCheck(String regCheck) {
      this.regCheck = regCheck;
   }

   public String getRejectReason() {
      return rejectReason;
   }

   public void setRejectReason(String rejectReason) {
      this.rejectReason = rejectReason;
   }

   public String getAcc_image() {
      return acc_image;
   }

   public void setAcc_image(String acc_image) {
      this.acc_image = acc_image;
   }

   public int getRo_price() {
      return ro_price;
   }

   public void setRo_price(int ro_price) {
      this.ro_price = ro_price;
   }

public String getCheckIn() {
	return checkIn;
}

public void setCheckIn(String checkIn) {
	this.checkIn = checkIn;
}

public String getCheckOut() {
	return checkOut;
}

public void setCheckOut(String checkOut) {
	this.checkOut = checkOut;
}

}