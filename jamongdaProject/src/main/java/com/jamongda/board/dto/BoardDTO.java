package com.jamongda.board.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("boardDTO")
public class BoardDTO {
	private int boardId;
	private String category;
	private String title;
	private String content;
	private Date regDate;
	
	public int getBoardId() {
		return boardId;
	}
	
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getRegDate() {
		return regDate;
	}
	
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}
