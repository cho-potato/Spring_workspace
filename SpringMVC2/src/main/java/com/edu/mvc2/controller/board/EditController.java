package com.edu.mvc2.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.edu.mvc2.domain.Board;
import com.edu.mvc2.model.board.BoardService;

import lombok.Setter;

//게시판의 글수정 요청을 처리하는 하위 컨트롤러..
//2.X까지 이 방식을 사용
@Setter
public class EditController implements Controller{
	private BoardService boardService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String board_idx=request.getParameter("board_idx");
		String title=request.getParameter("title");
		String writer=request.getParameter("writer");
		String content=request.getParameter("content");
		
		Board board=new Board();
		board.setBoard_idx(Integer.parseInt(board_idx));
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		//3단계 : 일시키기
		boardService.update(board);
		
		//저장해도 되고 재접속해도 되고!
		/*//4단계 : 저장할 거 없음->하지만 리스트를 위해 저장해주자?
		ModelAndView mav=new ModelAndView();
		mav.addObject("board", board);
		mav.setViewName("/board/detail");*/
		
		//저장없이 재접속!
		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:/board/detail?board_idx="+board_idx);
		return mav;
	}

}
