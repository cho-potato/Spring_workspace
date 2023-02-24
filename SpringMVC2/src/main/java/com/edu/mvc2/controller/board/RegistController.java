package com.edu.mvc2.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.edu.mvc2.domain.Board;
import com.edu.mvc2.model.board.BoardService;

import lombok.Setter;

//게시판의 글쓰기 요청을 처리하는 하위 컨트롤러..
//2.X까지 이 방식을 사용
@Setter
public class RegistController implements Controller{
	private BoardService boardService;
	

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String title=request.getParameter("title");
		String writer=request.getParameter("writer");
		String content=request.getParameter("content");
		
		Board board=new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		//3단계 : 일시키기
		boardService.insert(board);
		
		//4단계 : 저장할 사항 없음(redirect)
	
		ModelAndView mav=new ModelAndView();
		//개발자가 redirect를 명시하지 않으면 default가 포워딩방식이다.
		//따라서 redirect를 명시하자!
		mav.setViewName("redirect:/board/list");
	
		return mav;
	}

}
