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

// 게시판의 목록 요청을 처리하는 하위 컨트롤러
// 2.XX 버전까지 써온 고전적인 방식 
@Setter
public class ListController implements Controller {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName()); // 메모리에 올리기
	private BoardService boardService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("게시판 목록을 처리할게요");
		
		// 3단계) 일 시키기
		List boardList = boardService.selectAll();
		
		// 4단계) 저장
		// 원래  request에 저장해야 하지만 ModelAndView에 저장하면 request에 자동으로 저장되도록 처리되어 있다

		ModelAndView mav = new ModelAndView(); // 변경가능성 없는 고정된 데이터는 new 해도 됨
		
		mav.addObject("boardList", boardList); // 4단계) 결과 저장
		// mav.setViewName("/WEB-INF/views/   board/list   .jsp"); 앞 뒤 날리기
		// -> bean 설정 파일(beanname.xml)에 들어가서 view 매핑 방식도 정해놔야 함
		
		mav.setViewName("board/list"); // redirect를 명시하지 않았기 때문에 이건 forwarding
		
		return mav;
	}

}
