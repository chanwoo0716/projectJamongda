package com.jamongda.board.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.jamongda.board.dto.BoardDTO;

public interface BoardService {
	public Map boardList(Map<String,Integer> pagingMap) throws Exception;
	public int addBoard(BoardDTO boardDTO) throws DataAccessException;
	public BoardDTO viewBoard(int boardId) throws DataAccessException;
	public void modBoard(BoardDTO boardDTO) throws DataAccessException;
	public void removeBoard(int boardId) throws DataAccessException;
}