package com.edu.mvc2.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.edu.mvc2.domain.Board;
import com.edu.mvc2.model.board.BoardService;

import lombok.Setter;

// 게시판의 글삭제 요청을 처리하는 하위 컨트롤러
// 2.XX 버전까지 써온 고전적인 방식 
@Setter
public class DeleteController implements Controller {
	private BoardService boardService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 // 파라미터를 넘겨 받아야 일을 시킬 수 있는거 아시죠
		String board_idx = request.getParameter("board_idx");
		
		// 3단계) 일 시키기
		boardService.delete(Integer.parseInt(board_idx));
		
		// 4단계) 결과 저장 // 저장할거 없으니까 별도 명시 안함
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/board/list"); // 재접속
			
		return mav;
	}

}
