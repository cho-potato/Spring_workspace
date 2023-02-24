package com.edu.mvc2.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.edu.mvc2.domain.Board;
import com.edu.mvc2.model.board.BoardService;

import lombok.Setter;

//게시판의 상세보기 요청을 처리하는 하위 컨트롤러..
//2.X까지 이 방식을 사용
@Setter
public class DetailController implements Controller{
	Logger logger=LoggerFactory.getLogger(this.getClass().getName());
	private BoardService boardService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//logger.info("상세보기를 처리할게요");
		
		String board_idx=request.getParameter("board_idx");
		
		//3단계 : 일시키기
		Board board=boardService.select(Integer.parseInt(board_idx));
		
		//4단계 : 결과 저장(jsp로 가져갈 결과)
		ModelAndView mav=new ModelAndView();
		mav.addObject("board", board);//포워딩
		mav.setViewName("board/detail");
		
		return mav;
	}

}
