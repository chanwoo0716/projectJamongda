package com.jamongda.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jamongda.board.dao.BoardDAO;
import com.jamongda.board.dto.BoardDTO;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
	
	//의존성 주입
	@Autowired
	private BoardDAO boardDAO;
	
	
	@Override
	public Map boardList(Map<String, Integer> pagingMap) throws Exception {
		Map boardMap = new HashMap<>();
		int section = pagingMap.get("section");
		int pageNum = pagingMap.get("pageNum");
		int count = (section-1)*100+(pageNum-1)*10;//글 전체 개수
		
		List<BoardDTO> boardList = boardDAO.selectAllboards();
		int totBoards = boardDAO.selectToBoards();
		boardMap.put("boardList", boardList);
		boardMap.put("totBoards", totBoards);
		
		return boardMap;
	}

	@Override
	public int addBoard(BoardDTO boardDTO) throws DataAccessException {
		int boardId = boardDAO.getNewBoardId();
		boardDTO.setBoardId(boardId);
		boardDAO.insertNewBoard(boardDTO);
		return boardId;
	}

	@Override
	public BoardDTO viewBoard(int boardId) throws DataAccessException {
		BoardDTO boardDTO = boardDAO.selectBoard(boardId);
		
		return boardDTO;
	}

	@Override
	public void modBoard(BoardDTO boardDTO) throws DataAccessException {
		boardDAO.updateBoard(boardDTO);
		
	}

	@Override
	public void removeBoard(int boardId) throws DataAccessException {
		boardDAO.deleteBoard(boardId);
		
	}
	
	
	
}