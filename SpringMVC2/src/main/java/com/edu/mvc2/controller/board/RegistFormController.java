package com.edu.mvc2.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

//게시판의 글쓰기 요청을 처리하는 하위 컨트롤러..
//2.X까지 이 방식을 사용
public class RegistFormController implements Controller{

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//게시판 등록 폼의 이름 등록
		ModelAndView mav=new ModelAndView();
		mav.setViewName("board/regist");//내부디렉토리 접근하기
		
		return mav;
	}

}
