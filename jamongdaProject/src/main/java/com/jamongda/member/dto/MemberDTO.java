package com.jamongda.member.dto;

import org.springframework.stereotype.Component;

@Component("memberDTO")
public class MemberDTO {
	private String gu_email;
	private char admin_check='Y';
	
	public String getGu_email() {
		return gu_email;
	}
	public void setGu_email(String gu_email) {
		this.gu_email = gu_email;
	}
	public char getAdmin_check() {
		return admin_check;
	}
	public void setAdmin_check(char admin_check) {
		this.admin_check = admin_check;
	}
	
	
}
