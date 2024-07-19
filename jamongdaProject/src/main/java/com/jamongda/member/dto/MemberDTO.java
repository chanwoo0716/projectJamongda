package com.jamongda.member.dto;

import org.springframework.stereotype.Component;

@Component("memberDTO")
public class MemberDTO {

   private String email;
   private String name;
   private String pwd;
   private String tel;
   private String address;
   private String birth;
   private String regNumber;
   private String joinDate;
   private String role;
   
   public MemberDTO() {
   
   }
   
   public String getEmail() {
      return email;
   }
   public void setEmail(String email) {
      this.email = email;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getPwd() {
      return pwd;
   }
   public void setPwd(String pwd) {
      this.pwd = pwd;
   }
   public String getTel() {
      return tel;
   }
   public void setTel(String tel) {
      this.tel = tel;
   }
   public String getAddress() {
      return address;
   }
   public void setAddress(String address) {
      this.address = address;
   }
   public String getBirth() {
      return birth;
   }
   public void setBirth(String birth) {
      this.birth = birth;
   }
   public String getRegNumber() {
      return regNumber;
   }
   public void setRegNumber(String regNumber) {
      this.regNumber = regNumber;
   }
   public String getJoinDate() {
      return joinDate;
   }
   public void setJoinDate(String joinDate) {
      this.joinDate = joinDate;
   }
   public String getRole() {
      return role;
   }
   public void setRole(String role) {
      this.role = role;
   }
   
   
}