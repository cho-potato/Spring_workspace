package com.edu.mvc2.controller.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.edu.mvc2.model.board.BoardService;

import lombok.Setter;

//게시판의 목록 요청을 처리하는 하위 컨트롤러..
//2.X까지 이 방식을 사용
@Setter
public class ListController implements Controller{
	Logger logger=LoggerFactory.getLogger(this.getClass().getName());
	private BoardService boardService;
	
	//3,4단계
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("게시판 목록을 처리할게요");
		
		//3단계 : 일시키기
		
		List boardList=boardService.selectAll();
		
		//4단계 : 저장, 원래는 request에 저장해야 하지만 ModelAndView에 저장하면 request
		//자동으로 저장하도록 처리되어있다.
		
		ModelAndView mav=new ModelAndView();
		//mav.setViewName("/WEB-INF/views/  board/list  .jsp");
		mav.addObject("boardList", boardList);
		mav.setViewName("board/list");//포워딩 Why? 재접속을 명시하지 않으면 디폴트인 포워딩
		//자바코드안에 경로를 두면 안되는 이유? 경로 및 파일명이 변경될 수 있어서
		//스프링 방식 : 알맹이만 반환하면, 접두어 접미어는 스프링에서 처리해줌
		
		return mav;
	}
}
