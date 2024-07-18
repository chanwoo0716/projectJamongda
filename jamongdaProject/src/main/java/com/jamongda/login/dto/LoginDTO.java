package com.jamongda.login.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("loginDTO")
public class LoginDTO {
	private String email;
	private String pwd;
	
	public LoginDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public LoginDTO(String email,String pwd) {
		this.email=email;
		this.pwd=pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
	
	
}
