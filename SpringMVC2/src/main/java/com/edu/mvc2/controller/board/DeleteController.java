package com.edu.mvc2.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.edu.mvc2.model.board.BoardService;

import lombok.Setter;

//게시판의 글삭제 요청을 처리하는 하위 컨트롤러..
//2.X까지 이 방식을 사용
@Setter
public class DeleteController implements Controller{
	private BoardService boardService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String board_idx=request.getParameter("board_idx");
		System.out.println("board_idx?"+board_idx);
		
		//3단계
		boardService.delete(Integer.parseInt(board_idx));
		
		//4단계 없음
		
		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:/board/list");
		
		return mav;
	}

}
