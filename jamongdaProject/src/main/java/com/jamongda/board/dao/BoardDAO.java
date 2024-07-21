package com.jamongda.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.jamongda.board.dto.BoardDTO;

@Mapper
@Repository("boardDAO")
public interface BoardDAO {
	public List selectAllboards(@Param("count") int count) throws DataAccessException;
	public int selectToBoards() throws DataAccessException;
	public int getNewBoardId() throws DataAccessException;
	public void insertNewBoard(BoardDTO boardDTO) throws DataAccessException;
	public BoardDTO selectBoard(int board_id) throws DataAccessException;
	public void updateBoard(BoardDTO boardDTO) throws DataAccessException;
	public void deleteBoard(int board_id) throws DataAccessException;
}