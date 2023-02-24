package com.edu.springmvc1.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springmvc1.model.emp.EmpService;

import lombok.Setter;

// @(어노테이션) : JDK5부터 지원되는 주석 중의 하나. 우리가 알고 있는 일반적인 주석과는 달리
// 프로그램에서 사용되는 주석 
@Controller
@Setter
public class EmpController { // 3,4 단계를 처리할 하위 컨트롤러
	// System.out.println(); 대신 쓰는거 
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	// 서비스 준비
	// EmpServiceImpl empService;
	// 상위 자료형을 보유해야, 즉 DI를 구현해야 유지보수성이 높아짐,,, 결합도 낮아짐 ,,,
	
	// 자동엮음 기능으로 인해 empService의 인스턴스가 자동 주입된다
	@Autowired // 스프링이 자동으로 넣어줌
	private EmpService empService;
	
	// CRUD를 이 클래스에서 모두 처리할 수 있다
	// ListController, DetailController,,, 등등 따로 만들 필요 없다
	// Spring 3.0 이후부터는 implements 안해도 되게끔 만들어짐
	
	// 이 메서드가 어떤 URI를 처리할지 URI 매핑
	// @RequestMapping("내가 원하는 주소")
	@RequestMapping("/emps")
	public String getList(Model model) { // Model : 데이터를 저장할 수 있는 객체
		logger.info("게시판 목록을 처리할 예ㅖㅖㅖㅖㅖ쩡ㅇㅇㅇㅇㅇㅇ");
		
		// 3단계) 알맞은 객체에 일 시키기 (서비스에게 시키기)
		List empList = empService.selectAll(); // 가져오는 거니까 4단계 ㄱ
		
		// 4단계) 결과 저장
		// 1) 직접 request 객체를 이용하는 방법
		// 2) 간접적으로 request 객체를 이용하는 방법
		model.addAttribute("empList", empList);
		
		// 개발자가 redirect 키워드를 명시하지 않으면 디폴트가 forward
		
		
		
		// return "/WEB-INF/views/board/board_list.jsp";
		// 접두(prefix) 접미(suffix) 제거한 결과
		// return "board/board_list";
		return "emp/list";
	}
	
	
	
	
}
