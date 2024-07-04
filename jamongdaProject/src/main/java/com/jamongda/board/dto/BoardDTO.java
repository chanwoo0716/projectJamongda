package com.jamongda.board.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("boardDTO")
public class BoardDTO {
	private int boardId;
	private String boardCategory;
	private String boardTitle;
	private String boardContent;
	private Date boardWrite;
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	
	public String getBoardCategory() {
		return boardCategory;
	}
	public void setBoardCategory(String boardCategory) {
		this.boardCategory = boardCategory;
	}
	
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	
	public Date getBoardWrite() {
		return boardWrite;
	}
	public void setBoardWrite(Date boardWrite) {
		this.boardWrite = boardWrite;
	}
	
	
}
