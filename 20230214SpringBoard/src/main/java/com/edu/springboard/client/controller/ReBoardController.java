package com.edu.springboard.client.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springboard.domain.ReBoard;
import com.edu.springboard.exception.ReBoardException;
import com.edu.springboard.model.reboard.ReBoardService;

// 답변게시판의 CRUD를 수행할 하위 컨트롤러
// component-scan의 대상이 됨
@Controller
public class ReBoardController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired // xml에 bean을 등록한 적 없으므로, 자동 주입 받자
	private ReBoardService reboardService;
	
	// 목록 요청 처리 
	@RequestMapping(value="/reboard/list", method=RequestMethod.GET)
	public ModelAndView getList() {
		logger.info("목록요청받음");
		
		// 3단계 : 일 시키기
		List reboardList= reboardService.selectAll();
		
		// 4단계 : 결과 저장
		ModelAndView mav = new ModelAndView("reboard/list");
		mav.addObject("reboardList", reboardList);
		
		return mav;
	}
	// 글쓰기 폼 요청
	@GetMapping("/reboard/registform") // 단축형
	public ModelAndView registForm() {
		return new ModelAndView("reboard/regist"); // 단순 링크니까 한 줄로
	}
	
	// 글쓰기 요청 처리
	@PostMapping("/reboard/regist") // 단축형
	public ModelAndView regist(ReBoard reboard) { // 파라미터 받기
		
		// 3단계 : 일 시키기
		reboardService.insert(reboard);
		
		// 4단계 : 생략
		return new ModelAndView("redirect:/reboard/list");
	}
	
	// 상세보기 요청처리
	@GetMapping("/reboard/detail")
	public ModelAndView getDetail(int reboard_idx) {
		
		// 3단계 : 일 시키기
		ReBoard reboard= reboardService.select(reboard_idx);
		
		// 4단계 : 결과 저장
		ModelAndView mav = new ModelAndView("reboard/detail");
		mav.addObject("reboard", reboard);
		return mav;
	}
	
	// 삭제하기 요청처리
	// @RequestMapping(value="reboard/delete", method=RequestMethod.POST)
	@PostMapping("/reboard/delete")
	// public String del(int reboard_idx) {
	public ModelAndView del(int reboard_idx) {
		
		// 3단계 : 일 시키기
		reboardService.delete(reboard_idx);
		
		// 4단계 : 생략
		// return "redirect:/reboard/list";
		return new ModelAndView("redirect:/reboard/list");
	}
	
	// 수정하기 요청처리
	@PostMapping("reboard/edit")
	public ModelAndView edit(ReBoard reboard) {
		
		// 3단계 : 일 시키기
		reboardService.update(reboard);
		
		// 4단계 : 결과 저장
		return new ModelAndView("reboard/detail?reboard_idx="+reboard.getReboard_idx());
		
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("redirect:/reboard/detail?reboard_idx="+reboard.getReboard_idx());
//		return mav;
	}
	
	
	// 답변등록요청
	/*
	 * 서비스의 존재가 없을 경우, 컨트롤러느 업무를 구체적으로 처리해야 하므로 모델과 컨트롤러와 경계가 모호해진다.
	 * 따라서, 추후 모델만 다른 어플리케이션에 옮길 경우 모델의 업무를 일부 수행하고 있는 컨트롤러까지 같이 가져가게 된다
	 * 따라서 Controller는 Model의 역할을 수행해서는 안되며,
	 * 이러한 이유 때문에 Service가 존재해야 하며, Service는 트랜잭션 처리도 담당한다(DAO 여러 개를 거느려야 하기 때문에) 
	 */
	@RequestMapping(value="/reboard/reply", method=RequestMethod.POST)
	public ModelAndView reply() {
		
		return null;
	}
	
	// 글쓰기, 수정, 삭제의 경우 ReBoardException을 처리
	@ExceptionHandler(ReBoardException.class)
	public ModelAndView handle(ReBoardException e) {
		
		// 에러 저장
		ModelAndView mav = new ModelAndView();
		mav.addObject("e", e);
		mav.setViewName("error/result");
		return mav;                                                
	}
}
