package com.jamongda.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamongda.board.dto.BoardDTO;
import com.jamongda.board.service.BoardService;
import com.jamongda.board.service.BoardServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller("boardController")
public class BoardControllerImpl implements BoardController{
	
	//의존성 주입
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardDTO boardDTO;
	
	//board 조회 메서드
	@Override
	@RequestMapping(value="/board/boardList.do")
	public ModelAndView listBoards(@RequestParam(value="section", required = false) String _section, @RequestParam(value="pageNum", required = false) String _pageNum,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//글 목록이 많을 경우 10개씩 묶어서 노출되고 아래에 다음 목록으로 넘어갈 수 있는 번호가 나온다.
		_section = request.getParameter("section");//첫번째 섹션에
		_pageNum = request.getParameter("pageNum");//한 페이지의 
		int section = Integer.parseInt((_section==null)?"1":_section);
		int pageNum = Integer.parseInt((_pageNum==null)?"1":_pageNum);
		Map<String, Integer> pagingMap = new HashMap<String, Integer>();//<섹션 이름,섹션값>
		
		pagingMap.put("section", section);
		pagingMap.put("pageNum", pageNum);
		
		Map<String, Object> boardMap = boardService.boardList(pagingMap);
		boardMap.put("section", section);
		boardMap.put("pageNum", pageNum);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/boardList");
		mav.addObject("boardMap", boardMap);
		return mav;
	}
	
	//board 생성 폼으로 이동하는 메서드
	@Override
	@GetMapping("/board/boardForm.do")
	public ModelAndView boardForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/boardForm");
		return mav;
	}	
	
	//FAQ 페이지로 이동하는 메서드
	@Override
	@GetMapping("/board/FAQ.do")
	public ModelAndView FAQ(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/FAQ");
		return mav;
	}
	
	
	//board 게시글 추가 메서드
	@Override
	@PostMapping("/board/addBoard.do")
	public ModelAndView addBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("editorTxt");
		
		System.out.println("Category: " + category);
	    System.out.println("Title: " + title);
	    System.out.println("Content: " + content);
	    
		
		boardDTO.setBoard_category(category);
		boardDTO.setBoard_title(title);
		boardDTO.setBoard_content(content);
		
		int board_id = boardService.addBoard(boardDTO);
		ModelAndView mav = new ModelAndView("redirect:/board/viewBoard.do?board_id=" + board_id);
		return mav;
	}

	//board 상세글 보는 메서드
	@Override
	@GetMapping("/board/viewBoard.do")
	public ModelAndView viewBoard(@RequestParam("board_id") int boardId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		boardDTO=boardService.viewBoard(boardId);
		
		ModelAndView mav= new ModelAndView();
		mav.setViewName("/board/viewBoard");
		mav.addObject("board",boardDTO);
		return mav;
	}

	//board 상세글 수정하는 메서드
	/*@Override
	@PostMapping("/board/modBoardForm.do")
	public ModelAndView modBoard(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("editorTxt");
		String boardId = request.getParameter("boardId");
		
		System.out.println("Category: " + category);
	    System.out.println("Title: " + title);
	    System.out.println("Content: " + content);
	    
		
		boardDTO.setCategory(category);
		boardDTO.setTitle(title);
		boardDTO.setContent(content);
		boardDTO.setContent(boardId);
		
		boardService.modBoard(boardDTO);
		ModelAndView mav = new ModelAndView("redirect:/board/viewBoard.do?boardId="+boardId);
		return mav;
	}*/
	
	//board 수정폼으로 이동하는 메서드
	@Override
	@GetMapping("/board/modBoardForm.do")
	public ModelAndView modBoardForm(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/modBoardForm");
		mav.addObject("board",boardDTO);
		return mav;
	}
	
	
	//board 수정폼에 있는 수정한 내용을 저장하는 메서드
	@Override
	@PostMapping("/board/modBoard.do")
	public ModelAndView modBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> boardeMap = new HashMap<String, Object>();
		String board_id = (String) boardeMap.get("board_id");
		
		String category = request.getParameter("board_category");
		String title = request.getParameter("board_title");
		String content = request.getParameter("editorTxt");
		
		System.out.println("Category: " + category);
	    System.out.println("Title: " + title);
	    System.out.println("Content: " + content);
	    
		
		boardDTO.setBoard_category(category);
		boardDTO.setBoard_title(title);
		boardDTO.setBoard_content(content);
		
		boardService.modBoard(boardDTO);
		ModelAndView mav = new ModelAndView("redirect:/board/boardList.do");
		return mav;
	}
	
	//board 상세글 삭제하는 메서드
	@Override
	@PostMapping("/board/removeBoard.do")
	public ModelAndView removeBoard(@RequestParam("board_id") int board_id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boardService.removeBoard(board_id);
		ModelAndView mav = new ModelAndView("redirect:/board/boardList.do");
		return mav;
	}

	
	
	
	
	
	
	
	}
